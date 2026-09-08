package com.pichitube.app.feature.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.*
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.db.dao.WatchLaterDao
import com.pichitube.app.core.data.db.entity.HistoryEntity
import com.pichitube.app.core.data.db.entity.WatchLaterEntity
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.network.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

enum class PlayerDisplayMode {
    HIDDEN,
    COLLAPSED, // Mini-Player docked above bottom bar
    EXPANDED   // Full Player screen
}

data class PlayerState(
    val videoId: String = "",
    val streamInfo: StreamInfo? = null,
    val displayMode: PlayerDisplayMode = PlayerDisplayMode.HIDDEN,
    val isPlaying: Boolean = false,
    val currentPositionMs: Long = 0L,
    val durationMs: Long = 0L,
    val bufferedPositionMs: Long = 0L,
    val activeStreamUrl: String = "",
    val selectedQuality: String = "Auto",
    val resolvedQuality: String = "",
    val availableQualities: List<String> = emptyList(),
    val selectedAudioLanguage: String = "Original",
    val availableAudioLanguages: List<String> = emptyList(),
    val selectedSubtitle: Subtitle? = null,
    val availableSubtitles: List<Subtitle> = emptyList(),
    val playbackSpeed: Float = 1.0f,
    val isLooping: Boolean = false,
    val sleepTimerMinutes: Int? = null, // -1 means "End of video"
    val sponsorBlockEnabled: Boolean = true,
    val sponsorSegments: List<SponsorSegment> = emptyList(),
    val isInWatchLater: Boolean = false,
    val isSubscribed: Boolean = false,
    val showDescription: Boolean = false,
    val previewThumbnailUrl: String? = null,
    val previewTitle: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val isAudioOnly: Boolean get() = selectedQuality.equals("Audio Only", ignoreCase = true)
}

@OptIn(UnstableApi::class)
@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val extractorService: ExtractorService,
    private val sponsorBlockService: SponsorBlockService,
    private val historyDao: HistoryDao,
    private val watchLaterDao: WatchLaterDao,
    private val prefsRepository: PrefsRepository,
    private val sharedPlaybackState: SharedPlaybackState,
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // High performance mobile buffer control: smooth buffering without choking sockets
    private val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            /* minBufferMs = */ 15_000,
            /* maxBufferMs = */ 50_000,
            /* bufferForPlaybackMs = */ 1_500,
            /* bufferForPlaybackAfterRebufferMs = */ 3_000
        )
        .setBackBuffer(15_000, true)
        .build()

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                .build(),
            /* handleAudioFocus = */ true
        )
        .setWakeMode(C.WAKE_MODE_NETWORK)
        .setHandleAudioBecomingNoisy(true)
        .setLoadControl(loadControl)
        .build()

    // System MediaSession for notification shade & lockscreen controls
    val mediaSession: MediaSession = MediaSession.Builder(context, exoPlayer).build()

    private val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
        .setConnectTimeoutMs(20_000)
        .setReadTimeoutMs(20_000)
        .setAllowCrossProtocolRedirects(true)
        .setKeepPostFor302Redirects(true)

    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    private var sleepTimerJob: Job? = null
    private var positionTrackerJob: Job? = null
    private var sponsorBlockJob: Job? = null
    private var errorRecoveryCount = 0

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _state.update { it.copy(isPlaying = isPlaying) }
                sharedPlaybackState.update { copy(isPlaying = isPlaying) }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                val duration = exoPlayer.duration.coerceAtLeast(0L)
                _state.update { it.copy(durationMs = duration) }
                sharedPlaybackState.update { copy(durationMs = duration) }

                if (playbackState == Player.STATE_ENDED) {
                    // Check "Stop after this video" sleep timer option
                    if (_state.value.sleepTimerMinutes == -1) {
                        _state.update { it.copy(sleepTimerMinutes = null, isPlaying = false) }
                        exoPlayer.pause()
                        return
                    }

                    if (!_state.value.isLooping) {
                        _state.update { it.copy(isPlaying = false) }
                        sharedPlaybackState.update { copy(isPlaying = false) }
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                Timber.w(error, "Playback error detected: ${error.errorCodeName}")
                val videoId = _state.value.videoId
                val pos = exoPlayer.currentPosition
                if (videoId.isNotBlank() && errorRecoveryCount < 1) {
                    errorRecoveryCount++
                    Timber.i("Fallback recovery #$errorRecoveryCount for video $videoId")
                    scope.launch {
                        delay(600)
                        val stream = _state.value.streamInfo
                        val muxed = stream?.videoStreams?.filter { !it.isVideoOnly && it.url.isNotBlank() }
                            ?.maxByOrNull { it.quality.extractResolutionInt() }
                        if (muxed != null) {
                            _state.update { it.copy(selectedQuality = "Auto", resolvedQuality = muxed.quality) }
                            applyMediaToPlayer(muxed.url, null, _state.value.selectedSubtitle, pos)
                            exoPlayer.play()
                        }
                    }
                } else if (errorRecoveryCount >= 1) {
                    _state.update { it.copy(error = "Playback error. Tap to retry.", isLoading = false) }
                }
            }
        })

        // High frequency position tracker for smooth scrubber & history saving
        positionTrackerJob = scope.launch {
            var historyCounter = 0
            while (true) {
                delay(250)
                val pos = exoPlayer.currentPosition
                val dur = exoPlayer.duration.coerceAtLeast(0L)
                val buf = exoPlayer.bufferedPosition
                _state.update {
                    it.copy(
                        currentPositionMs = pos,
                        durationMs = dur,
                        bufferedPositionMs = buf,
                    )
                }
                sharedPlaybackState.update {
                    copy(
                        positionMs = pos,
                        durationMs = dur,
                    )
                }

                // Auto SponsorBlock skip check
                val segments = _state.value.sponsorSegments
                if (_state.value.sponsorBlockEnabled && segments.isNotEmpty() && exoPlayer.isPlaying) {
                    for (segment in segments) {
                        if (pos in segment.startMs..segment.endMs) {
                            Timber.d("Skipping sponsor segment: ${segment.category} -> ${segment.endMs}ms")
                            exoPlayer.seekTo(segment.endMs + 100)
                            break
                        }
                    }
                }

                // Periodically save watch position to history
                historyCounter++
                if (historyCounter >= 40) { // every ~10s
                    historyCounter = 0
                    val currentVideoId = _state.value.videoId
                    if (currentVideoId.isNotBlank()) {
                        historyDao.updatePosition(currentVideoId, pos)
                    }
                }
            }
        }
    }

    fun playVideo(
        videoId: String,
        previewThumbnailUrl: String? = null,
        previewTitle: String? = null,
    ) {
        val current = _state.value
        // If already playing this video, bring to front
        if (current.videoId == videoId && current.streamInfo != null) {
            _state.update { it.copy(displayMode = PlayerDisplayMode.EXPANDED) }
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
            }
            return
        }

        // Load new video
        errorRecoveryCount = 0
        scope.launch {
            _state.update {
                it.copy(
                    videoId = videoId,
                    previewThumbnailUrl = previewThumbnailUrl ?: it.streamInfo?.takeIf { s -> s.videoId == videoId }?.thumbnailUrl,
                    previewTitle = previewTitle ?: it.streamInfo?.takeIf { s -> s.videoId == videoId }?.title,
                    isLoading = true,
                    error = null,
                    displayMode = PlayerDisplayMode.EXPANDED,
                )
            }

            val inWatchLater = watchLaterDao.contains(videoId) > 0
            _state.update { it.copy(isInWatchLater = inWatchLater) }

            val streamResult = extractorService.getStreamInfo(videoId)
            streamResult.fold(
                onSuccess = { stream ->
                    val prefs = prefsRepository.preferences.first()

                    val hlsUrl = stream.hlsUrl
                    val bestAudio = stream.audioStreams
                        .filter { it.url.isNotBlank() }
                        .maxByOrNull { it.averageBitrate }

                    // Prefer reliable progressive muxed stream for "Auto"
                    val bestMuxed = stream.videoStreams
                        .filter { !it.isVideoOnly && it.url.isNotBlank() }
                        .maxByOrNull { it.quality.extractResolutionInt() }

                    val defaultVideoUrl: String
                    val defaultAudioUrl: String?
                    val defaultResolvedQuality: String

                    if (bestMuxed != null) {
                        defaultVideoUrl = bestMuxed.url
                        defaultAudioUrl = null
                        defaultResolvedQuality = bestMuxed.quality
                    } else {
                        val best = stream.videoStreams
                            .filter { it.url.isNotBlank() }
                            .maxByOrNull { it.quality.extractResolutionInt() }
                        val isVideoOnly = best?.isVideoOnly == true || (best?.quality?.extractResolutionInt() ?: 0) >= 1080
                        defaultVideoUrl = hlsUrl?.takeIf { it.isNotBlank() } ?: best?.url.orEmpty()
                        defaultAudioUrl = if (hlsUrl.isNullOrBlank() && isVideoOnly) bestAudio?.url else null
                        defaultResolvedQuality = best?.quality.orEmpty()
                    }

                    val qualities = mutableListOf("Auto")
                    qualities.addAll(
                        stream.videoStreams
                            .map { it.quality }
                            .filter { it.isNotBlank() }
                            .distinct()
                            .sortedByDescending { it.extractResolutionInt() }
                    )
                    if (stream.audioStreams.isNotEmpty()) {
                        qualities.add("Audio Only")
                    }

                    val audioLangs = stream.audioStreams
                        .map { it.languageName }
                        .filter { it.isNotBlank() }
                        .distinct()
                        .ifEmpty { listOf("Original") }

                    // Look up existing watch position for THIS video (not previous video)
                    val existingHistory = historyDao.getById(videoId)
                    val resumePosition = if (existingHistory != null && existingHistory.positionMs > 5_000 &&
                        (existingHistory.durationSeconds == 0L || existingHistory.positionMs < (existingHistory.durationSeconds * 1000L) - 10_000)
                    ) {
                        existingHistory.positionMs
                    } else {
                        0L
                    }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            streamInfo = stream,
                            activeStreamUrl = defaultVideoUrl,
                            selectedQuality = "Auto",
                            resolvedQuality = defaultResolvedQuality,
                            availableQualities = qualities,
                            selectedAudioLanguage = audioLangs.firstOrNull() ?: "Original",
                            availableAudioLanguages = audioLangs,
                            selectedSubtitle = null,
                            availableSubtitles = stream.subtitles,
                            sponsorBlockEnabled = prefs.sponsorBlockEnabled,
                            error = null,
                        )
                    }

                    // Prepare ExoPlayer starting from 0 (or saved history for THIS video)
                    applyMediaToPlayer(defaultVideoUrl, defaultAudioUrl, null, resumePosition)

                    // Sync shared playback state
                    sharedPlaybackState.update {
                        copy(
                            videoId = videoId,
                            title = stream.title,
                            channelName = stream.channelName,
                            thumbnailUrl = stream.thumbnailUrl,
                            isVisible = true,
                            isPlaying = true,
                        )
                    }

                    // Save to history
                    historyDao.insert(
                        HistoryEntity(
                            videoId = videoId,
                            title = stream.title,
                            channelName = stream.channelName,
                            channelId = stream.channelId,
                            thumbnailUrl = stream.thumbnailUrl,
                            durationSeconds = stream.durationSeconds,
                            viewCount = stream.viewCount,
                        )
                    )

                    // Load SponsorBlock segments
                    if (prefs.sponsorBlockEnabled) {
                        launch {
                            val segments = sponsorBlockService.getSegments(videoId)
                            _state.update { it.copy(sponsorSegments = segments) }
                        }
                    }
                },
                onFailure = { e ->
                    Timber.e(e, "Failed to extract stream for videoId=$videoId")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load video: ${e.localizedMessage ?: "Unknown error"}"
                        )
                    }
                }
            )
        }
    }

    private fun applyMediaToPlayer(
        videoUrl: String,
        audioUrl: String?,
        subtitle: Subtitle?,
        startPositionMs: Long = 0L,
    ) {
        if (videoUrl.isBlank()) return
        val stream = _state.value.streamInfo

        val mediaMetadata = MediaMetadata.Builder()
            .setTitle(stream?.title ?: "PichiTube Video")
            .setArtist(stream?.channelName ?: "")
            .setArtworkUri(Uri.parse(stream?.thumbnailUrl ?: ""))
            .build()

        val mediaItemBuilder = MediaItem.Builder()
            .setUri(videoUrl)
            .setMediaMetadata(mediaMetadata)

        subtitle?.let { sub ->
            val subtitleConfig = MediaItem.SubtitleConfiguration.Builder(Uri.parse(sub.url))
                .setMimeType(MimeTypes.TEXT_VTT)
                .setLanguage(sub.code)
                .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
                .build()
            mediaItemBuilder.setSubtitleConfigurations(listOf(subtitleConfig))
        }

        val mediaItem = mediaItemBuilder.build()

        // If 4K, 1440p, or 1080p video-only stream, merge with separate audio stream
        if (!audioUrl.isNullOrBlank() && audioUrl != videoUrl) {
            val videoSource = ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
            val audioMediaItem = MediaItem.Builder()
                .setUri(audioUrl)
                .setMediaMetadata(mediaMetadata)
                .build()
            val audioSource = ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(audioMediaItem)
            val mergedSource = MergingMediaSource(true, true, videoSource, audioSource)
            exoPlayer.setMediaSource(mergedSource)
        } else if (videoUrl.contains(".m3u8")) {
            val hlsSource = HlsMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
            exoPlayer.setMediaSource(hlsSource)
        } else {
            val source = ProgressiveMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem)
            exoPlayer.setMediaSource(source)
        }

        if (startPositionMs > 0) {
            exoPlayer.seekTo(startPositionMs)
        } else {
            exoPlayer.seekTo(0L)
        }
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        // Start Foreground PlayerService for persistent notification
        try {
            val serviceIntent = Intent(context, PlayerService::class.java)
            context.startService(serviceIntent)
        } catch (e: Exception) {
            Timber.w(e, "PlayerService start failed")
        }
    }

    fun minimize() {
        _state.update { it.copy(displayMode = PlayerDisplayMode.COLLAPSED) }
        sharedPlaybackState.update { copy(isVisible = true) }
    }

    fun expand() {
        _state.update { it.copy(displayMode = PlayerDisplayMode.EXPANDED) }
    }

    fun close() {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        _state.update { PlayerState(displayMode = PlayerDisplayMode.HIDDEN) }
        sharedPlaybackState.dismiss()
    }

    fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    fun seekTo(positionMs: Long) {
        val target = positionMs.coerceIn(0L, exoPlayer.duration.coerceAtLeast(0L))
        exoPlayer.seekTo(target)
    }

    fun seekRelative(deltaMs: Long) {
        seekTo(exoPlayer.currentPosition + deltaMs)
    }

    fun setQuality(quality: String) {
        val stream = _state.value.streamInfo ?: return
        val bestAudio = stream.audioStreams.maxByOrNull { it.averageBitrate }

        val (videoUrl, audioUrl, resolved) = when {
            quality.equals("Auto", ignoreCase = true) -> {
                val bestMuxed = stream.videoStreams
                    .filter { !it.isVideoOnly && it.url.isNotBlank() }
                    .maxByOrNull { it.quality.extractResolutionInt() }

                if (bestMuxed != null) {
                    Triple(bestMuxed.url, null, bestMuxed.quality)
                } else {
                    val best = stream.videoStreams.filter { it.url.isNotBlank() }.maxByOrNull { it.quality.extractResolutionInt() }
                    val isVideoOnly = best?.isVideoOnly == true || (best?.quality?.extractResolutionInt() ?: 0) >= 1080
                    Triple(best?.url.orEmpty(), if (isVideoOnly) bestAudio?.url else null, best?.quality.orEmpty())
                }
            }
            quality.equals("Audio Only", ignoreCase = true) -> {
                Triple(bestAudio?.url.orEmpty(), null, "Audio")
            }
            else -> {
                val target = stream.videoStreams.firstOrNull { it.quality.equals(quality, ignoreCase = true) }
                    ?: stream.videoStreams.maxByOrNull { it.quality.extractResolutionInt() }
                val isVideoOnly = target?.isVideoOnly == true || (target?.quality?.extractResolutionInt() ?: 0) >= 1080
                Triple(target?.url.orEmpty(), if (isVideoOnly) bestAudio?.url else null, target?.quality.orEmpty())
            }
        }

        if (videoUrl.isNotBlank()) {
            val currentPos = exoPlayer.currentPosition
            _state.update {
                it.copy(
                    selectedQuality = quality,
                    resolvedQuality = resolved,
                    activeStreamUrl = videoUrl
                )
            }
            applyMediaToPlayer(videoUrl, audioUrl, _state.value.selectedSubtitle, currentPos)
        }
    }

    fun playAsAudioOnly() {
        setQuality("Audio Only")
        minimize()
    }

    fun setAudioLanguage(languageName: String) {
        val stream = _state.value.streamInfo ?: return
        val target = stream.audioStreams.firstOrNull { it.languageName == languageName }
        if (target != null && target.url.isNotBlank()) {
            _state.update { it.copy(selectedAudioLanguage = languageName) }
            val currentRes = _state.value.selectedQuality
            val isVideoOnly = currentRes.extractResolutionInt() >= 1080
            if (isVideoOnly) {
                applyMediaToPlayer(_state.value.activeStreamUrl, target.url, _state.value.selectedSubtitle, exoPlayer.currentPosition)
            }
        }
    }

    fun setSubtitle(subtitle: Subtitle?) {
        _state.update { it.copy(selectedSubtitle = subtitle) }
        setQuality(_state.value.selectedQuality)
    }

    fun setSpeed(speed: Float) {
        exoPlayer.playbackParameters = PlaybackParameters(speed)
        _state.update { it.copy(playbackSpeed = speed) }
    }

    fun setPlaybackSpeed(speed: Float) = setSpeed(speed)

    fun toggleLoop() {
        val nextLoop = !_state.value.isLooping
        exoPlayer.repeatMode = if (nextLoop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        _state.update { it.copy(isLooping = nextLoop) }
    }

    fun setSleepTimer(minutes: Int?) {
        sleepTimerJob?.cancel()
        _state.update { it.copy(sleepTimerMinutes = minutes) }

        if (minutes != null && minutes > 0) {
            sleepTimerJob = scope.launch {
                delay(minutes * 60 * 1000L)
                exoPlayer.pause()
                _state.update { it.copy(sleepTimerMinutes = null, isPlaying = false) }
            }
        }
        // If minutes == -1 ("End of video"), handled in onPlaybackStateChanged
    }

    fun toggleSponsorBlock() {
        val next = !_state.value.sponsorBlockEnabled
        _state.update { it.copy(sponsorBlockEnabled = next) }
        scope.launch { prefsRepository.setSponsorBlock(next) }
    }

    fun toggleWatchLater() {
        val videoId = _state.value.videoId
        val stream = _state.value.streamInfo ?: return
        scope.launch {
            val exists = watchLaterDao.contains(videoId) > 0
            if (exists) {
                watchLaterDao.deleteById(videoId)
                _state.update { it.copy(isInWatchLater = false) }
            } else {
                watchLaterDao.insert(
                    WatchLaterEntity(
                        videoId = videoId,
                        title = stream.title,
                        channelName = stream.channelName,
                        channelId = stream.channelId,
                        thumbnailUrl = stream.thumbnailUrl,
                        durationSeconds = stream.durationSeconds,
                        viewCount = stream.viewCount,
                    )
                )
                _state.update { it.copy(isInWatchLater = true) }
            }
        }
    }

    fun toggleDescription() {
        _state.update { it.copy(showDescription = !it.showDescription) }
    }
}
