package com.pichitube.app.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.channel.ChannelExtractor
import org.schabi.newpipe.extractor.search.SearchExtractor
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor
import org.schabi.newpipe.extractor.stream.StreamExtractor
import org.schabi.newpipe.extractor.channel.ChannelInfoItem
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import org.schabi.newpipe.extractor.stream.VideoStream as NpVideoStream
import org.schabi.newpipe.extractor.stream.AudioStream as NpAudioStream
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExtractorService @Inject constructor(
    private val downloader: NewPipeDownloader,
) {
    private val youtube = ServiceList.YouTube

    // ─── Search ───────────────────────────────────────────────────────────────

    suspend fun search(
        query: String,
        contentFilter: String = "videos",
    ): Result<SearchResult> = withContext(Dispatchers.IO) {
        runCatching {
            val extractor: SearchExtractor = youtube.getSearchExtractor(query)
            extractor.fetchPage()
            val items = extractor.initialPage.items
            val allItems = mutableListOf<SearchResultItem>()
            val videoResults = mutableListOf<VideoInfo>()

            items.forEach { item ->
                when (item) {
                    is StreamInfoItem -> {
                        val video = item.toVideoInfo()
                        videoResults.add(video)
                        allItems.add(SearchResultItem.VideoItem(video))
                    }
                    is ChannelInfoItem -> {
                        val channelId = item.url?.substringAfterLast("/") ?: ""
                        val avatarUrl = try { item.thumbnails?.firstOrNull()?.url ?: "" } catch (e: Exception) { "" }
                        allItems.add(
                            SearchResultItem.ChannelItem(
                                channelId = channelId,
                                title = item.name ?: "",
                                avatarUrl = avatarUrl,
                                subscriberCount = try { item.subscriberCount } catch (e: Exception) { 0L },
                                videoCount = try { item.streamCount } catch (e: Exception) { 0L },
                                description = try { item.description ?: "" } catch (e: Exception) { "" }
                            )
                        )
                    }
                    is PlaylistInfoItem -> {
                        val playlistId = item.url?.substringAfter("list=")?.substringBefore("&") ?: item.url ?: ""
                        val thumb = try { item.thumbnails?.firstOrNull()?.url ?: "" } catch (e: Exception) { "" }
                        allItems.add(
                            SearchResultItem.PlaylistItem(
                                playlistId = playlistId,
                                title = item.name ?: "",
                                thumbnailUrl = thumb,
                                uploaderName = try { item.uploaderName ?: "" } catch (e: Exception) { "" },
                                videoCount = try { item.streamCount } catch (e: Exception) { 0L }
                            )
                        )
                    }
                }
            }

            SearchResult(
                results = videoResults,
                items = allItems,
                nextPageToken = null
            )
        }.onFailure { Timber.e(it, "Search failed: $query") }
    }

    suspend fun getSearchSuggestions(query: String): List<String> = withContext(Dispatchers.IO) {
        if (query.isBlank()) return@withContext emptyList()
        runCatching {
            val suggestionExtractor = youtube.suggestionExtractor
            suggestionExtractor.suggestionList(query)
        }.getOrElse { e ->
            Timber.w(e, "Search suggestion failed for: $query")
            emptyList()
        }
    }

    // ─── Trending / Personalized Feed ─────────────────────────────────────────

    suspend fun getTrending(region: String = "US"): Result<List<VideoInfo>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val extractor = youtube.getKioskList().getExtractorByUrl(
                    "https://www.youtube.com/feed/trending", null
                )
                extractor.fetchPage()
                extractor.initialPage.items
                    .filterIsInstance<StreamInfoItem>()
                    .map { it.toVideoInfo() }
            }.onFailure { Timber.e(it, "Trending failed") }
        }

    suspend fun getPersonalizedFeed(queries: List<String>): Result<List<VideoInfo>> =
        withContext(Dispatchers.IO) {
            runCatching {
                coroutineScope {
                    val deferredList = queries.take(5).map { q ->
                        async {
                            search(q).getOrNull()?.results ?: emptyList()
                        }
                    }
                    val results = deferredList.awaitAll().flatten()
                    results.distinctBy { it.videoId }.shuffled()
                }
            }.onFailure { Timber.e(it, "Personalized feed failed") }
        }

    suspend fun searchMultiple(queries: List<String>): List<VideoInfo> =
        withContext(Dispatchers.IO) {
            runCatching {
                coroutineScope {
                    val deferredList = queries.map { q ->
                        async {
                            search(q).getOrNull()?.results ?: emptyList()
                        }
                    }
                    deferredList.awaitAll().flatten().distinctBy { it.videoId }
                }
            }.getOrElse { emptyList() }
        }

    // ─── Stream Info (for playback) ───────────────────────────────────────────

    suspend fun getStreamInfo(videoId: String): Result<StreamInfo> =
        withContext(Dispatchers.IO) {
            runCatching {
                val url = "https://www.youtube.com/watch?v=$videoId"
                val extractor: StreamExtractor = youtube.getStreamExtractor(url)
                extractor.fetchPage()

                val videoStreams = try {
                    val muxed = extractor.videoStreams.map { it.toVideoStream(isVideoOnly = false) }
                    val videoOnly = extractor.videoOnlyStreams.map { it.toVideoStream(isVideoOnly = true) }
                    muxed + videoOnly
                } catch (e: Exception) { emptyList() }

                val audioStreams = try {
                    extractor.audioStreams.map { it.toAudioStream() }
                } catch (e: Exception) { emptyList() }

                val hlsUrl = try { extractor.hlsUrl } catch (e: Exception) { null }
                val dashUrl = try { extractor.dashMpdUrl } catch (e: Exception) { null }

                val relatedItems = try {
                    extractor.relatedItems?.items
                        ?.filterIsInstance<StreamInfoItem>()
                        ?.take(20)
                        ?.map { it.toVideoInfo() } ?: emptyList()
                } catch (e: Exception) { emptyList() }

                val thumbUrl = try {
                    extractor.thumbnails.maxByOrNull { (it.width ?: 0) }?.url ?: ""
                } catch (e: Exception) { "https://i.ytimg.com/vi/$videoId/hqdefault.jpg" }

                val channelAvatarUrl = try {
                    extractor.uploaderAvatars.firstOrNull()?.url ?: ""
                } catch (e: Exception) { "" }

                val subtitles: List<SubtitleTrack> = try {
                    extractor.subtitlesDefault?.map { sub ->
                        SubtitleTrack(
                            url = try { sub.content ?: sub.url ?: "" } catch (e: Exception) { "" },
                            languageCode = try { sub.languageTag ?: sub.locale?.language ?: "en" } catch (e: Exception) { "en" },
                            languageName = try { sub.displayLanguageName ?: sub.locale?.displayName ?: "English" } catch (e: Exception) { "English" },
                            isAutoGenerated = try { sub.isAutoGenerated } catch (e: Exception) { false },
                        )
                    } ?: emptyList<SubtitleTrack>()
                } catch (e: Exception) { emptyList<SubtitleTrack>() }

                StreamInfo(
                    videoId = videoId,
                    title = try { extractor.name } catch (e: Exception) { "" },
                    channelName = try { extractor.uploaderName } catch (e: Exception) { "" },
                    channelId = try { extractor.uploaderUrl?.substringAfterLast("/") ?: "" } catch (e: Exception) { "" },
                    channelAvatarUrl = channelAvatarUrl,
                    description = try { extractor.description?.content ?: "" } catch (e: Exception) { "" },
                    videoStreams = videoStreams,
                    audioStreams = audioStreams,
                    hlsUrl = hlsUrl?.takeIf { it.isNotBlank() },
                    dashManifestUrl = dashUrl?.takeIf { it.isNotBlank() },
                    thumbnailUrl = thumbUrl,
                    durationSeconds = try { extractor.length } catch (e: Exception) { 0L },
                    viewCount = try { extractor.viewCount } catch (e: Exception) { 0L },
                    likeCount = try { extractor.likeCount } catch (e: Exception) { 0L },
                    uploadDate = try { extractor.textualUploadDate ?: "" } catch (e: Exception) { "" },
                    subscriberCount = try { extractor.uploaderSubscriberCount } catch (e: Exception) { 0L },
                    subtitles = subtitles,
                    relatedVideos = relatedItems,
                )
            }.onFailure { Timber.e(it, "StreamInfo failed for $videoId") }
        }

    // ─── Channel ──────────────────────────────────────────────────────────────

    suspend fun getChannel(channelId: String): Result<ChannelInfo> =
        withContext(Dispatchers.IO) {
            runCatching {
                val url = "https://www.youtube.com/channel/$channelId"
                val extractor: ChannelExtractor = youtube.getChannelExtractor(url)
                extractor.fetchPage()
                val videos = try {
                    val tab = extractor.tabs.firstOrNull()
                    if (tab != null) {
                        val tabExtractor = youtube.getChannelTabExtractor(tab)
                        tabExtractor.fetchPage()
                        tabExtractor.initialPage.items.filterIsInstance<StreamInfoItem>().map { it.toVideoInfo() }
                    } else emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
                ChannelInfo(
                    channelId = channelId,
                    name = try { extractor.name } catch (e: Exception) { "" },
                    avatarUrl = try { extractor.avatars.firstOrNull()?.url ?: "" } catch (e: Exception) { "" },
                    bannerUrl = try { extractor.banners.firstOrNull()?.url ?: "" } catch (e: Exception) { "" },
                    description = try { extractor.description ?: "" } catch (e: Exception) { "" },
                    subscriberCount = try { extractor.subscriberCount } catch (e: Exception) { 0L },
                    videos = videos,
                )
            }.onFailure { Timber.e(it, "Channel failed: $channelId") }
        }

    // ─── Shorts feed ──────────────────────────────────────────────────────────

    suspend fun getShortsFeed(region: String = "BD"): Result<List<VideoInfo>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val isBD = region.equals("BD", ignoreCase = true)
                val primaryQuery = if (isBD) "#shorts viral bangla" else "#shorts viral"
                val primaryResult = search(primaryQuery).getOrNull()?.results.orEmpty()

                var filtered = primaryResult.filter { item ->
                    item.isShort ||
                    item.title.contains("#shorts", ignoreCase = true) ||
                    item.title.contains("#short", ignoreCase = true) ||
                    item.title.contains("shorts", ignoreCase = true) ||
                    (item.durationSeconds in 1..90L) ||
                    (item.durationSeconds <= 0L)
                }

                // If primary query returned sparse results, fetch secondary query sequentially without socket congestion
                if (filtered.size < 6) {
                    val secondaryQuery = "#shorts trending"
                    val secondaryResult = search(secondaryQuery).getOrNull()?.results.orEmpty()
                    val secondaryFiltered = secondaryResult.filter { item ->
                        item.isShort ||
                        item.title.contains("#shorts", ignoreCase = true) ||
                        item.title.contains("#short", ignoreCase = true) ||
                        item.title.contains("shorts", ignoreCase = true) ||
                        (item.durationSeconds in 1..90L) ||
                        (item.durationSeconds <= 0L)
                    }
                    filtered = (filtered + secondaryFiltered).distinctBy { it.videoId }
                }

                // If still empty (e.g. strict mobile carrier CGNAT blocking search endpoint), fallback to Trending Kiosk
                if (filtered.isEmpty()) {
                    val trending = getTrending(region).getOrNull().orEmpty()
                    val shortTrending = trending.filter { it.isShort || (it.durationSeconds in 1..90L) }
                    if (shortTrending.isNotEmpty()) {
                        filtered = shortTrending
                    } else if (trending.isNotEmpty()) {
                        filtered = trending.take(15)
                    }
                }

                if (filtered.isNotEmpty()) {
                    filtered.distinctBy { it.videoId }.shuffled()
                } else {
                    primaryResult.distinctBy { it.videoId }
                }
            }.onFailure { Timber.e(it, "Failed to get shorts feed") }
        }

    // ─── Extensions ───────────────────────────────────────────────────────────

    private fun StreamInfoItem.toVideoInfo() = VideoInfo(
        videoId = url.substringAfter("v=").substringBefore("&").also { if (it.isBlank()) url else it }
            .let { if (it.startsWith("http")) url.substringAfter("v=").substringBefore("&") else it }
            .let { extractVideoId(url) },
        title = name ?: "",
        thumbnailUrl = thumbnails.maxByOrNull { it.width }?.url
            ?: "https://i.ytimg.com/vi/${extractVideoId(url)}/hqdefault.jpg",
        channelName = uploaderName ?: "",
        channelId = uploaderUrl?.substringAfterLast("/") ?: "",
        channelAvatarUrl = uploaderAvatars.firstOrNull()?.url ?: "",
        viewCount = viewCount,
        durationSeconds = duration,
        uploadDate = textualUploadDate ?: "",
        isShort = isShortFormContent ||
            url.contains("/shorts/") ||
            (name?.contains("#shorts", ignoreCase = true) == true) ||
            (name?.contains("#short", ignoreCase = true) == true) ||
            (duration in 1..90L),
        isLive = streamType == org.schabi.newpipe.extractor.stream.StreamType.LIVE_STREAM,
    )

    private fun NpVideoStream.toVideoStream(isVideoOnly: Boolean = false) = VideoStream(
        url = content ?: "",
        quality = resolution ?: "unknown",
        format = format?.toString() ?: "unknown",
        isVideoOnly = isVideoOnly,
    )

    private fun NpAudioStream.toAudioStream(): AudioStream {
        val langName = try {
            audioTrackName?.takeIf { it.isNotBlank() }
                ?: audioLocale?.displayName?.takeIf { it.isNotBlank() }
                ?: "Original"
        } catch (e: Exception) { "Original" }

        val langCode = try {
            audioLocale?.language ?: ""
        } catch (e: Exception) { "" }

        return AudioStream(
            url = content ?: "",
            quality = "${averageBitrate}kbps",
            format = format?.toString() ?: "unknown",
            averageBitrate = averageBitrate,
            languageCode = langCode,
            languageName = langName,
        )
    }

    fun updateRegion(regionCode: String) {
        try {
            NewPipe.init(
                downloader,
                org.schabi.newpipe.extractor.localization.Localization("en", regionCode),
                org.schabi.newpipe.extractor.localization.ContentCountry(regionCode)
            )
        } catch (e: Exception) {
            Timber.w(e, "Failed to update localization for region $regionCode")
        }
    }

    private fun extractVideoId(url: String): String {
        return try {
            when {
                url.contains("v=") -> url.substringAfter("v=").substringBefore("&").take(11)
                url.contains("youtu.be/") -> url.substringAfter("youtu.be/").substringBefore("?").take(11)
                url.contains("/shorts/") -> url.substringAfter("/shorts/").substringBefore("?").take(11)
                url.contains("/watch/") -> url.substringAfter("/watch/").substringBefore("?").take(11)
                else -> url.substringAfterLast("/").substringBefore("?").take(11)
            }
        } catch (e: Exception) { "" }
    }
}
