package com.pichitube.app.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.network.ExtractorService
import com.pichitube.app.core.network.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

import com.pichitube.app.core.network.SearchResultItem

enum class SearchSort(val label: String) {
    RELEVANCE("Relevance"),
    UPLOAD_DATE("Upload date"),
    VIEW_COUNT("View count"),
    RATING("Rating")
}

enum class SearchUploadDate(val label: String) {
    ANY("Any time"),
    LAST_HOUR("Last hour"),
    TODAY("Today"),
    THIS_WEEK("This week"),
    THIS_MONTH("This month"),
    THIS_YEAR("This year")
}

enum class SearchDuration(val label: String) {
    ANY("Any duration"),
    UNDER_4_MIN("Under 4 minutes"),
    FROM_4_TO_20_MIN("4 - 20 minutes"),
    OVER_20_MIN("Over 20 minutes")
}

enum class SearchType(val label: String) {
    ALL("All"),
    VIDEO("Video"),
    CHANNEL("Channel"),
    PLAYLIST("Playlist"),
    SHORTS("Shorts")
}

data class SearchFilter(
    val sortBy: SearchSort = SearchSort.RELEVANCE,
    val uploadDate: SearchUploadDate = SearchUploadDate.ANY,
    val duration: SearchDuration = SearchDuration.ANY,
    val type: SearchType = SearchType.ALL,
) {
    val isDefault: Boolean
        get() = sortBy == SearchSort.RELEVANCE &&
                uploadDate == SearchUploadDate.ANY &&
                duration == SearchDuration.ANY &&
                type == SearchType.ALL
}

data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val isLoadingSuggestions: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val results: List<SearchResultItem> = emptyList(),
    val rawResults: List<SearchResultItem> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val filter: SearchFilter = SearchFilter(),
    val isFilterSheetOpen: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val extractorService: ExtractorService,
    private val prefsRepository: PrefsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private var suggestionJob: Job? = null

    init {
        viewModelScope.launch {
            prefsRepository.preferences.collect { prefs ->
                _uiState.update { it.copy(recentSearches = prefs.searchHistory) }
            }
        }
    }

    fun onQueryChange(q: String) {
        _uiState.update { it.copy(query = q, error = null) }
        suggestionJob?.cancel()

        if (q.isBlank()) {
            searchJob?.cancel()
            _uiState.update {
                it.copy(
                    results = emptyList(),
                    rawResults = emptyList(),
                    suggestions = emptyList(),
                    isSearching = false,
                    isLoadingSuggestions = false
                )
            }
            return
        }

        // Live autocomplete suggestion with 250ms debounce
        suggestionJob = viewModelScope.launch {
            delay(250)
            _uiState.update { it.copy(isLoadingSuggestions = true) }
            val suggestions = extractorService.getSearchSuggestions(q)
            _uiState.update {
                it.copy(
                    suggestions = suggestions,
                    isLoadingSuggestions = false
                )
            }
        }
    }

    fun search(query: String = _uiState.value.query) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return

        suggestionJob?.cancel()
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSearching = true,
                    query = trimmed,
                    suggestions = emptyList(),
                    error = null
                )
            }
            prefsRepository.addSearchQuery(trimmed)
            extractorService.search(trimmed).fold(
                onSuccess = { result ->
                    val raw: List<SearchResultItem> = if (result.items.isNotEmpty()) {
                        result.items
                    } else {
                        result.results.map { SearchResultItem.VideoItem(it) }
                    }
                    val filtered = applyFilter(raw, _uiState.value.filter)
                    _uiState.update {
                        it.copy(
                            isSearching = false,
                            rawResults = raw,
                            results = filtered,
                            suggestions = emptyList()
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(
                            isSearching = false,
                            error = e.message ?: "Search failed. Check your connection."
                        )
                    }
                }
            )
        }
    }

    fun openFilterSheet() {
        _uiState.update { it.copy(isFilterSheetOpen = true) }
    }

    fun closeFilterSheet() {
        _uiState.update { it.copy(isFilterSheetOpen = false) }
    }

    fun applyFilterSelection(newFilter: SearchFilter) {
        val filtered = applyFilter(_uiState.value.rawResults, newFilter)
        _uiState.update {
            it.copy(
                filter = newFilter,
                results = filtered,
                isFilterSheetOpen = false
            )
        }
    }

    fun resetFilter() {
        val defaultFilter = SearchFilter()
        val filtered = applyFilter(_uiState.value.rawResults, defaultFilter)
        _uiState.update {
            it.copy(
                filter = defaultFilter,
                results = filtered,
                isFilterSheetOpen = false
            )
        }
    }

    private fun applyFilter(items: List<SearchResultItem>, filter: SearchFilter): List<SearchResultItem> {
        var filtered = items

        // 1. Type
        filtered = when (filter.type) {
            SearchType.ALL -> filtered
            SearchType.VIDEO -> filtered.filter { it is SearchResultItem.VideoItem && !it.video.isShort }
            SearchType.CHANNEL -> filtered.filterIsInstance<SearchResultItem.ChannelItem>()
            SearchType.PLAYLIST -> filtered.filterIsInstance<SearchResultItem.PlaylistItem>()
            SearchType.SHORTS -> filtered.filter { it is SearchResultItem.VideoItem && it.video.isShort }
        }

        // 2. Duration
        if (filter.duration != SearchDuration.ANY) {
            filtered = filtered.filter { item ->
                when (item) {
                    is SearchResultItem.VideoItem -> when (filter.duration) {
                        SearchDuration.UNDER_4_MIN -> item.video.durationSeconds in 1..240L
                        SearchDuration.FROM_4_TO_20_MIN -> item.video.durationSeconds in 241..1200L
                        SearchDuration.OVER_20_MIN -> item.video.durationSeconds > 1200L
                        else -> true
                    }
                    else -> false
                }
            }
        }

        // 3. Upload Date (matched against textualUploadDate)
        if (filter.uploadDate != SearchUploadDate.ANY) {
            filtered = filtered.filter { item ->
                when (item) {
                    is SearchResultItem.VideoItem -> {
                        val d = item.video.uploadDate.lowercase()
                        when (filter.uploadDate) {
                            SearchUploadDate.LAST_HOUR -> d.contains("minute") || d.contains("second")
                            SearchUploadDate.TODAY -> d.contains("minute") || d.contains("hour") || d.contains("today")
                            SearchUploadDate.THIS_WEEK -> d.contains("minute") || d.contains("hour") || d.contains("day") || d.contains("yesterday")
                            SearchUploadDate.THIS_MONTH -> !d.contains("year") && (d.contains("day") || d.contains("week") || d.contains("hour") || d.contains("month"))
                            SearchUploadDate.THIS_YEAR -> !d.contains("years") || d.contains("1 year")
                            else -> true
                        }
                    }
                    else -> true
                }
            }
        }

        // 4. Sort By
        filtered = when (filter.sortBy) {
            SearchSort.RELEVANCE -> filtered
            SearchSort.VIEW_COUNT -> filtered.sortedByDescending { item ->
                when (item) {
                    is SearchResultItem.VideoItem -> item.video.viewCount
                    is SearchResultItem.ChannelItem -> item.subscriberCount
                    is SearchResultItem.PlaylistItem -> item.videoCount
                }
            }
            SearchSort.UPLOAD_DATE -> filtered.sortedBy { item ->
                when (item) {
                    is SearchResultItem.VideoItem -> {
                        val d = item.video.uploadDate.lowercase()
                        when {
                            d.contains("second") -> 1
                            d.contains("minute") -> 2
                            d.contains("hour") -> 3
                            d.contains("day") -> 4
                            d.contains("week") -> 5
                            d.contains("month") -> 6
                            d.contains("year") -> 7
                            else -> 8
                        }
                    }
                    else -> 9
                }
            }
            SearchSort.RATING -> filtered.sortedByDescending { item ->
                when (item) {
                    is SearchResultItem.VideoItem -> item.video.likeCount
                    else -> 0L
                }
            }
        }

        return filtered
    }

    fun removeRecentSearch(query: String) {
        viewModelScope.launch {
            prefsRepository.removeSearchQuery(query)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            prefsRepository.clearSearchHistory()
        }
    }
}
