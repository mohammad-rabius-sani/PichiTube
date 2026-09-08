package com.pichitube.app.feature.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.db.dao.WatchLaterDao
import com.pichitube.app.core.data.db.entity.HistoryEntity
import com.pichitube.app.core.data.db.entity.WatchLaterEntity
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.network.ExtractorService
import com.pichitube.app.core.network.SponsorBlockService
import com.pichitube.app.core.network.SponsorSegment
import com.pichitube.app.core.network.StreamInfo
import com.pichitube.app.core.network.Subtitle
import com.pichitube.app.core.network.SubtitleTrack
import com.pichitube.app.core.network.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

data class PlayerUiState(
    val isLoading: Boolean = true,
    val streamInfo: StreamInfo? = null,
    val activeStreamUrl: String = "",
    val bestVideoUrl: String = "",
    val bestAudioUrl: String = "",
    val hlsUrl: String? = null,
    val selectedQuality: String = "Auto",
    val availableQualities: List<String> = emptyList(),
    val selectedAudioLanguage: String = "Original",
    val availableAudioLanguages: List<String> = emptyList(),
    val selectedSubtitle: Subtitle? = null,
    val availableSubtitles: List<Subtitle> = emptyList(),
    val playbackSpeed: Float = 1.0f,
    val isLooping: Boolean = false,
    val sleepTimerMinutes: Int? = null,
    val sponsorBlockEnabled: Boolean = true,
    val sponsorSegments: List<SponsorSegment> = emptyList(),
    val isInWatchLater: Boolean = false,
    val isSubscribed: Boolean = false,
    val showDescription: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val extractorService: ExtractorService,
    private val sponsorBlockService: SponsorBlockService,
    private val historyDao: HistoryDao,
    private val watchLaterDao: WatchLaterDao,
    private val prefsRepository: PrefsRepository,
    val sharedPlaybackState: SharedPlaybackState,
) : ViewModel() {

    private val videoId: String = checkNotNull(savedStateHandle["videoId"])

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    private var sleepTimerJob: Job? = null

    init {
        loadStream()
        viewModelScope.launch {
            _uiState.update { it.copy(isInWatchLater = watchLaterDao.contains(videoId) > 0) }
        }
    }

    fun loadStream() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val streamResult = extractorService.getStreamInfo(videoId)
            streamResult.fold(
                onSuccess = { stream ->
                    val prefs = prefsRepository.preferences.first()

                    // Pick best video+audio or HLS
                    val hlsUrl = stream.hlsUrl
                    val bestVideo = stream.videoStreams
                        .filter { it.url.isNotBlank() }
                        .sortedByDescending { it.quality.extractResolutionInt() }
                        .firstOrNull()
                    val bestAudio = stream.audioStreams
                        .filter { it.url.isNotBlank() }
                        .sortedByDescending { it.averageBitrate }
                        .firstOrNull()

                    val defaultUrl = hlsUrl?.takeIf { it.isNotBlank() }
                        ?: bestVideo?.url.orEmpty()

                    // Extract available qualities
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

                    // Extract available audio languages
                    val audioLangs = stream.audioStreams
                        .map { it.languageName }
                        .filter { it.isNotBlank() }
                        .distinct()
                        .ifEmpty { listOf("Original") }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            streamInfo = stream,
                            activeStreamUrl = defaultUrl,
                            bestVideoUrl = bestVideo?.url ?: "",
                            bestAudioUrl = bestAudio?.url ?: "",
                            hlsUrl = hlsUrl,
                            selectedQuality = "Auto",
                            availableQualities = qualities,
                            selectedAudioLanguage = audioLangs.firstOrNull() ?: "Original",
                            availableAudioLanguages = audioLangs,
                            availableSubtitles = stream.subtitles,
                            sponsorBlockEnabled = prefs.sponsorBlockEnabled,
                            error = null,
                        )
                    }

                    // Update shared state for mini-player
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

                    // SponsorBlock (background, non-blocking)
                    if (prefs.sponsorBlockEnabled) {
                        launch {
                            val segments = sponsorBlockService.getSegments(videoId)
                            _uiState.update { it.copy(sponsorSegments = segments) }
                        }
                    }
                },
                onFailure = { e ->
                    Timber.e(e, "Stream load failed")
                    _uiState.update {
                        it.copy(isLoading = false, error = "Failed to load video: ${e.message}")
                    }
                }
            )
        }
    }

    fun setQuality(quality: String) {
        val stream = _uiState.value.streamInfo ?: return
        val newUrl = when {
            quality.equals("Auto", ignoreCase = true) -> {
                stream.hlsUrl?.takeIf { it.isNotBlank() }
                    ?: stream.videoStreams.maxByOrNull { it.quality.extractResolutionInt() }?.url.orEmpty()
            }
            quality.equals("Audio Only", ignoreCase = true) -> {
                stream.audioStreams.maxByOrNull { it.averageBitrate }?.url.orEmpty()
            }
            else -> {
                stream.videoStreams.firstOrNull { it.quality.equals(quality, ignoreCase = true) }?.url
                    ?: stream.videoStreams.maxByOrNull { it.quality.extractResolutionInt() }?.url.orEmpty()
            }
        }
        if (newUrl.isNotBlank()) {
            _uiState.update {
                it.copy(
                    selectedQuality = quality,
                    activeStreamUrl = newUrl
                )
            }
        }
    }

    fun setAudioLanguage(language: String) {
        val stream = _uiState.value.streamInfo ?: return
        val targetAudio = stream.audioStreams.firstOrNull { it.languageName.equals(language, ignoreCase = true) }
        if (targetAudio != null && targetAudio.url.isNotBlank()) {
            _uiState.update {
                it.copy(
                    selectedAudioLanguage = language,
                    bestAudioUrl = targetAudio.url
                )
            }
        }
    }

    fun setSubtitle(subtitle: Subtitle?) {
        _uiState.update { it.copy(selectedSubtitle = subtitle) }
    }

    fun setPlaybackSpeed(speed: Float) {
        _uiState.update { it.copy(playbackSpeed = speed) }
    }

    fun toggleLoop() {
        _uiState.update { it.copy(isLooping = !it.isLooping) }
    }

    fun setSleepTimer(minutes: Int?, onExpire: () -> Unit) {
        sleepTimerJob?.cancel()
        _uiState.update { it.copy(sleepTimerMinutes = minutes) }
        if (minutes != null && minutes > 0) {
            sleepTimerJob = viewModelScope.launch {
                delay(minutes * 60 * 1000L)
                _uiState.update { it.copy(sleepTimerMinutes = null) }
                onExpire()
            }
        }
    }

    fun toggleSponsorBlock() {
        viewModelScope.launch {
            val current = _uiState.value.sponsorBlockEnabled
            val newSetting = !current
            prefsRepository.setSponsorBlock(newSetting)
            _uiState.update { it.copy(sponsorBlockEnabled = newSetting) }
            if (newSetting && _uiState.value.sponsorSegments.isEmpty()) {
                val segments = sponsorBlockService.getSegments(videoId)
                _uiState.update { it.copy(sponsorSegments = segments) }
            }
        }
    }

    fun updateWatchPosition(positionMs: Long) {
        viewModelScope.launch {
            historyDao.updatePosition(videoId, positionMs)
            sharedPlaybackState.update { copy(positionMs = positionMs) }
        }
    }

    fun toggleWatchLater() {
        viewModelScope.launch {
            val stream = _uiState.value.streamInfo ?: return@launch
            val inList = watchLaterDao.contains(videoId) > 0
            if (inList) {
                watchLaterDao.deleteById(videoId)
                _uiState.update { it.copy(isInWatchLater = false) }
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
                _uiState.update { it.copy(isInWatchLater = true) }
            }
        }
    }

    fun toggleDescription() {
        _uiState.update { it.copy(showDescription = !it.showDescription) }
    }

    private fun String.extractResolutionInt(): Int {
        return Regex("(\\d+)p").find(this)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    }
}
