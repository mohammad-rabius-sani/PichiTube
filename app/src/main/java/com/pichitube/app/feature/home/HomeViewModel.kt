package com.pichitube.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.network.ExtractorService
import com.pichitube.app.core.network.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

val HOME_CHIPS = listOf(
    "All",
    "Watched",
    "Recently uploaded",
    "Trending",
    "Music",
    "Gaming",
    "News",
    "Podcasts",
    "Live",
    "Comedy",
    "Gadgets",
    "Cooking",
    "Animation",
    "Sports",
    "Movies"
)

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val videos: List<VideoInfo> = emptyList(),
    val shorts: List<VideoInfo> = emptyList(),
    val selectedChip: String = "All",
    val currentRegion: String = "BD",
    val showShorts: Boolean = true,
    val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val extractorService: ExtractorService,
    private val prefsRepository: PrefsRepository,
    private val historyDao: HistoryDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var loadMoreJob: Job? = null
    private var feedPageIndex = 0

    private val categorySeedMap = mapOf(
        "Trending_BD" to listOf(
            listOf("trending in Bangladesh 2025 2026", "viral videos Bangladesh 2025", "top trending bangla 2025"),
            listOf("top bangla songs trending 2025", "viral clips bangladesh 2025 2026"),
            listOf("somoy news trending 2025", "popular entertainment bangladesh 2025")
        ),
        "Trending_GLOBAL" to listOf(
            listOf("trending videos today 2025 2026", "top viral clips today"),
            listOf("popular music 2025 2026", "trending entertainment worldwide 2025"),
            listOf("top viral news today", "popular today global 2025")
        ),
        "Music_BD" to listOf(
            listOf("top bangla songs 2025 2026", "coke studio bangla new 2025", "bollywood new songs 2025"),
            listOf("bangla romantic songs 2025", "hindi trending songs 2025", "bangla band hits 2025"),
            listOf("bangla lyrical music hits 2025", "popular acoustic bangla 2025")
        ),
        "Music_GLOBAL" to listOf(
            listOf("top billboard hits 2025", "popular official music video 2025 2026"),
            listOf("top trending music global 2025", "new song release 2025 2026"),
            listOf("acoustic pop live music 2025", "electronic dance music hits 2025")
        ),
        "Gaming" to listOf(
            listOf("gaming highlights gameplay 2025", "pubg mobile esports 2025"),
            listOf("pc gaming walkthrough 2025", "free fire tournament highlights 2025"),
            listOf("gta 5 gameplay funny moments 2025", "top new games 2025 trailer"),
            listOf("valorant highlights fps 2025", "minecraft gameplay epic build 2025")
        ),
        "News" to listOf(
            listOf("bangladesh news live updates today", "somoy news channel 24 live"),
            listOf("jamuna tv live news today", "international news live today"),
            listOf("special investigative news 2025", "current affairs talk show 2025")
        ),
        "Podcasts" to listOf(
            listOf("popular podcast full episode 2025", "joe rogan podcast clips 2025"),
            listOf("huberman lab clips 2025", "bangla talk show podcast 2025"),
            listOf("tech podcast discussion 2025", "true crime story podcast 2025")
        ),
        "Live" to listOf(
            listOf("live stream news now", "live cricket match 2025"),
            listOf("lofi hip hop radio live", "gaming live stream 2025")
        ),
        "Comedy" to listOf(
            listOf("bangla funny comedy video 2025", "funny pranks 2025"),
            listOf("standup comedy bangla hindi 2025", "funny clips compilations 2025"),
            listOf("comedy sketches parody 2025", "roasting funny videos 2025")
        ),
        "Gadgets" to listOf(
            listOf("smartphone review unboxing 2025 2026", "bangla tech review gadgets 2025"),
            listOf("best budget laptop phone 2025", "top future gadgets ai tools 2025"),
            listOf("pc build setup desk setup 2025", "apple vs samsung camera test 2025")
        ),
        "Cooking" to listOf(
            listOf("village cooking recipe 2025", "delicious easy cooking recipes 2025"),
            listOf("street food dhaka vlog 2025", "tasty quick dinner ideas 2025"),
            listOf("traditional biryani cooking recipe 2025", "restaurant style cooking tutorial 2025")
        ),
        "Animation" to listOf(
            listOf("cgi 3d animated short film 2025", "anime highlights fight scenes 2025"),
            listOf("blender animation award winning 2025", "pixar style animated shorts 2025")
        ),
        "Sports" to listOf(
            listOf("bangladesh cricket highlights 2025", "football champions league goals 2025"),
            listOf("cricket match highlights 2025", "messi ronaldo best goals 2025"),
            listOf("epic sports moments 2025", "sports impossible comeback 2025")
        ),
        "Movies" to listOf(
            listOf("movie trailers 2025 2026", "bangla new movie clips 2025"),
            listOf("hollywood movie trailers 4k 2025", "bollywood new movies scenes 2025 2026"),
            listOf("top action movie scenes 2025", "cinema review explanation 2025")
        )
    )

    // Regional mixed seeds: Natural blend of Bangla, Hindi/Bollywood, Pakistani, and Global (just like YouTube/Vanced)
    private val allFeedSeedsBD = listOf(
        listOf("trending in Bangladesh 2025 2026", "bollywood new songs 2025 2026", "bangla new natok 2025"),
        listOf("pakistani drama latest episode 2025", "bangla new songs 2025 2026", "viral videos Bangladesh 2025"),
        listOf("hindi trending songs 2025", "coke studio bangla new 2025", "bangladesh tech review gadgets 2025"),
        listOf("kapil sharma show new 2025", "bangladesh cricket highlights 2025", "dhaka street food vlog 2025"),
        listOf("t-series new song 2025", "somoy news live updates", "top bangla band music 2025"),
        listOf("hum tv drama new episode 2025", "bangla comedy natok 2025", "mrbeast new video 2025"),
        listOf("hindi movie trailer 2025 2026", "bangla documentary 2025", "world news live updates today")
    )

    private val allFeedSeedsGlobal = listOf(
        listOf("trending videos today 2025", "popular music 2025 2026"),
        listOf("tech review 2025 2026", "top viral clips today"),
        listOf("gaming highlights 2025", "world news live updates today"),
        listOf("movie trailers 2025 2026", "funny videos comedy 2025"),
        listOf("science discoveries space 2025", "sports top plays 2025"),
        listOf("street food worldwide 2025", "travel vlog 4k 2025"),
        listOf("future ai tech tools 2025", "documentary full episode 2025")
    )

    init {
        viewModelScope.launch {
            prefsRepository.preferences.collect { prefs ->
                _uiState.update { it.copy(showShorts = prefs.showShorts, currentRegion = prefs.contentRegion) }
                extractorService.updateRegion(prefs.contentRegion)
            }
        }
        loadFeed()
    }

    fun loadFeed(isRefresh: Boolean = false) {
        loadJob?.cancel()
        loadMoreJob?.cancel()
        feedPageIndex = 0
        loadJob = viewModelScope.launch {
            if (!isRefresh) _uiState.update { it.copy(isLoading = true, error = null) }
            else _uiState.update { it.copy(isRefreshing = true) }

            val prefs = prefsRepository.preferences.first()
            val region = prefs.contentRegion
            val isBangladesh = region.equals("BD", ignoreCase = true)

            // History & Search-Driven Recommendation
            val history = historyDao.getAll().firstOrNull() ?: emptyList()
            val searchHistory = prefs.searchHistory

            val personalizedQueries = mutableListOf<String>()

            // 1. Learn from user's most-watched channels (append "latest 2025" to avoid ancient videos)
            val topChannels = history.groupBy { it.channelName }
                .entries.sortedByDescending { it.value.size }
                .map { it.key }
                .filter { it.isNotBlank() }
                .take(3)
            for (channel in topChannels) {
                personalizedQueries.add("$channel latest 2025")
            }

            // 2. Learn from user's recent search queries
            for (search in searchHistory.take(3)) {
                if (search.isNotBlank()) personalizedQueries.add("$search new 2025")
            }

            // Concurrently fetch:
            // - Trending Kiosk (Freshness guarantee: 24-72h)
            // - Personalized Feed (Learned from search & history)
            // - Regional Mixed Content (Bangla, Hindi, PK, Global)
            // - Shorts
            val trendingDeferred = async {
                extractorService.getTrending(region).getOrElse { emptyList() }
            }
            val personalizedDeferred = async {
                if (personalizedQueries.isNotEmpty()) {
                    extractorService.getPersonalizedFeed(personalizedQueries).getOrElse { emptyList() }
                } else emptyList()
            }
            val regionalSeeds = if (isBangladesh) allFeedSeedsBD[0] else allFeedSeedsGlobal[0]
            val regionalDeferred = async {
                extractorService.searchMultiple(regionalSeeds)
            }
            val shortsDeferred = async {
                if (prefs.showShorts) {
                    val shortsSeed = if (isBangladesh) "shorts trending 2025 2026" else "youtube shorts trending 2025"
                    extractorService.search(shortsSeed).map { r ->
                        r.results.filter { it.isShort || it.durationSeconds in 1..65L }.take(12)
                    }.getOrDefault(emptyList())
                } else emptyList()
            }

            val trendingList = trendingDeferred.await().filter { !it.isShort && !it.isLive }
            val personalizedList = personalizedDeferred.await().filter { !it.isShort && !it.isLive }
            val regionalList = regionalDeferred.await().filter { !it.isShort && !it.isLive }
            val shorts = shortsDeferred.await()

            // Smart Interleaving:
            // [Top Fresh Trending 1..3]
            // -> [Recent Search / Watch Recs 1..2]
            // -> [Regional Multi-Cultural Mix (Bangla/Hindi/PK) 1..3]
            // -> [Next batch of Trending 4..6]
            // -> [More Personal Recs]
            // -> [Remaining Regional & Trending]
            val combinedVideos = mutableListOf<VideoInfo>()
            val seen = mutableSetOf<String>()

            fun addVideo(v: VideoInfo) {
                if (v.videoId.isNotBlank() && seen.add(v.videoId)) {
                    combinedVideos.add(v)
                }
            }

            trendingList.take(3).forEach { addVideo(it) }
            personalizedList.take(2).forEach { addVideo(it) }
            regionalList.take(3).forEach { addVideo(it) }
            trendingList.drop(3).take(3).forEach { addVideo(it) }
            personalizedList.drop(2).take(3).forEach { addVideo(it) }
            regionalList.drop(3).take(4).forEach { addVideo(it) }
            trendingList.drop(6).forEach { addVideo(it) }
            personalizedList.drop(5).forEach { addVideo(it) }
            regionalList.drop(7).forEach { addVideo(it) }

            feedPageIndex = 1

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    isRefreshing = false,
                    isLoadingMore = false,
                    videos = combinedVideos,
                    shorts = shorts,
                    selectedChip = "All",
                    currentRegion = region,
                    error = if (combinedVideos.isEmpty()) "Could not load feed. Please check your internet." else null,
                )
            }
        }
    }

    fun refresh() = loadFeed(isRefresh = true)

    fun setRegion(regionCode: String) {
        viewModelScope.launch {
            prefsRepository.setContentRegion(regionCode)
            extractorService.updateRegion(regionCode)
            _uiState.update { it.copy(currentRegion = regionCode) }
            loadFeed()
        }
    }

    fun selectChip(chip: String) {
        if (chip == _uiState.value.selectedChip) return
        _uiState.update { it.copy(selectedChip = chip) }
        if (chip == "All") {
            loadFeed()
            return
        }

        if (chip == "Watched") {
            loadJob?.cancel()
            loadMoreJob?.cancel()
            loadJob = viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val historyList = historyDao.getAll().firstOrNull() ?: emptyList()
                val videos = historyList.map { h ->
                    VideoInfo(
                        videoId = h.videoId,
                        title = h.title,
                        thumbnailUrl = h.thumbnailUrl,
                        channelName = h.channelName,
                        channelId = "",
                        durationSeconds = h.durationSeconds,
                        uploadDate = "",
                        viewCount = 0L,
                    )
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        videos = videos,
                        shorts = emptyList(),
                        error = if (videos.isEmpty()) "No watched videos yet" else null
                    )
                }
            }
            return
        }

        if (chip == "Recently uploaded") {
            loadJob?.cancel()
            loadMoreJob?.cancel()
            loadJob = viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val isBD = _uiState.value.currentRegion.equals("BD", ignoreCase = true)
                val queries = if (isBD) {
                    listOf("bangla new videos today 2025 2026", "new release bangla 2025")
                } else {
                    listOf("new uploaded videos today 2025", "latest video upload 2025")
                }
                val results = extractorService.searchMultiple(queries).filter { !it.isShort }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        videos = results,
                        shorts = emptyList(),
                        error = if (results.isEmpty()) "No recent uploads found" else null
                    )
                }
            }
            return
        }

        loadJob?.cancel()
        loadMoreJob?.cancel()
        feedPageIndex = 0

        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val isBD = _uiState.value.currentRegion.equals("BD", ignoreCase = true)

            val seedKey = when (chip) {
                "Trending" -> if (isBD) "Trending_BD" else "Trending_GLOBAL"
                "Music" -> if (isBD) "Music_BD" else "Music_GLOBAL"
                else -> chip
            }
            val batches = categorySeedMap[seedKey] ?: listOf(listOf(chip))
            val initialBatch = batches.firstOrNull() ?: listOf(chip)

            val results = extractorService.searchMultiple(initialBatch)
                .filter { !it.isShort }

            feedPageIndex = 1

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isLoadingMore = false,
                    videos = results,
                    shorts = emptyList(),
                    error = if (results.isEmpty()) "No videos found for $chip" else null,
                )
            }
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (state.isLoadingMore || state.isLoading || state.videos.isEmpty()) return

        loadMoreJob?.cancel()
        loadMoreJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            val isBD = _uiState.value.currentRegion.equals("BD", ignoreCase = true)
            val chip = _uiState.value.selectedChip

            val existingIds = _uiState.value.videos.map { it.videoId }.toMutableSet()
            val accumulatedNewVideos = mutableListOf<VideoInfo>()

            var attempts = 0
            while (accumulatedNewVideos.size < 12 && attempts < 3) {
                val queriesToRun: List<String> = if (chip == "All") {
                    val seeds = if (isBD) allFeedSeedsBD else allFeedSeedsGlobal
                    seeds[feedPageIndex % seeds.size]
                } else {
                    val seedKey = when (chip) {
                        "Trending" -> if (isBD) "Trending_BD" else "Trending_GLOBAL"
                        "Music" -> if (isBD) "Music_BD" else "Music_GLOBAL"
                        else -> chip
                    }
                    val batches = categorySeedMap[seedKey] ?: listOf(listOf(chip))
                    batches[feedPageIndex % batches.size]
                }

                feedPageIndex++
                attempts++

                val fetched = extractorService.searchMultiple(queriesToRun)
                    .filter { !it.isShort && !it.isLive }

                for (video in fetched) {
                    if (video.videoId !in existingIds) {
                        existingIds.add(video.videoId)
                        accumulatedNewVideos.add(video)
                    }
                }
            }

            _uiState.update { current ->
                current.copy(
                    isLoadingMore = false,
                    videos = current.videos + accumulatedNewVideos,
                )
            }
        }
    }

    fun markNotInterested(videoId: String) {
        _uiState.update { s ->
            s.copy(
                videos = s.videos.filter { it.videoId != videoId },
                shorts = s.shorts.filter { it.videoId != videoId },
            )
        }
    }
}
