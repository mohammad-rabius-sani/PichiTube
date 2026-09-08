package com.pichitube.app.feature.shorts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.network.ExtractorService
import com.pichitube.app.core.network.VideoInfo
import com.pichitube.app.core.network.extractResolutionInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

data class ShortsUiState(
    val isLoading: Boolean = true,
    val shorts: List<VideoInfo> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class ShortsViewModel @Inject constructor(
    private val extractorService: ExtractorService,
    private val prefsRepository: PrefsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShortsUiState())
    val uiState: StateFlow<ShortsUiState> = _uiState.asStateFlow()

    private val streamCache = ConcurrentHashMap<String, String>()

    init {
        loadShorts()
    }

    fun loadShorts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val prefs = prefsRepository.preferences.first()
            extractorService.getShortsFeed(prefs.contentRegion).fold(
                onSuccess = { list ->
                    _uiState.update { current ->
                        val combined = if (list.isNotEmpty()) list else current.shorts
                        current.copy(
                            isLoading = false,
                            shorts = combined,
                            error = if (combined.isEmpty()) "No Shorts available" else null
                        )
                    }
                    // Prefetch the first short stream URL
                    list.firstOrNull()?.let { firstItem ->
                        launch { getShortStream(firstItem.videoId) }
                    }
                },
                onFailure = { e ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            error = if (current.shorts.isEmpty()) (e.localizedMessage ?: "Failed to load Shorts") else null
                        )
                    }
                }
            )
        }
    }

    suspend fun getShortStream(videoId: String): String? {
        streamCache[videoId]?.let { return it }
        val stream = extractorService.getStreamInfo(videoId).getOrNull() ?: return null

        // 1. 720p or 480p muxed progressive stream with integrated audio is fastest & most reliable
        val bestMuxed = stream.videoStreams
            .filter { !it.isVideoOnly && it.url.isNotBlank() }
            .maxByOrNull { it.quality.extractResolutionInt() }

        val url = bestMuxed?.url
            ?: stream.hlsUrl?.takeIf { it.isNotBlank() }
            ?: stream.videoStreams.firstOrNull { it.url.isNotBlank() && !it.isVideoOnly }?.url
            ?: stream.videoStreams.firstOrNull { it.url.isNotBlank() }?.url

        if (url != null) {
            streamCache[videoId] = url
        }
        return url
    }

    fun loadMore() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            val prefs = prefsRepository.preferences.first()
            val more = extractorService.getShortsFeed(prefs.contentRegion).getOrNull().orEmpty()
            val existing = _uiState.value.shorts.map { it.videoId }.toSet()
            val newItems = more.filter { it.videoId !in existing }
            if (newItems.isNotEmpty()) {
                _uiState.update { it.copy(shorts = it.shorts + newItems) }
            }
        }
    }
}
