package com.pichitube.app.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.db.dao.WatchLaterDao
import com.pichitube.app.core.data.db.entity.HistoryEntity
import com.pichitube.app.core.data.db.entity.WatchLaterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryUiState(
    val history: List<HistoryEntity> = emptyList(),
    val watchLater: List<WatchLaterEntity> = emptyList(),
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val historyDao: HistoryDao,
    private val watchLaterDao: WatchLaterDao,
) : ViewModel() {

    val uiState: StateFlow<LibraryUiState> = combine(
        historyDao.getAll(),
        watchLaterDao.getAll(),
    ) { history, watchLater ->
        LibraryUiState(history = history, watchLater = watchLater)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LibraryUiState())

    fun clearHistory() = viewModelScope.launch { historyDao.deleteAll() }
    fun removeFromWatchLater(videoId: String) = viewModelScope.launch { watchLaterDao.deleteById(videoId) }
}
