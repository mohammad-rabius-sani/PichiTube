package com.pichitube.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.prefs.AppPreferences
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.data.prefs.ThemeMode
import com.pichitube.app.core.data.prefs.VideoQuality
import com.pichitube.app.core.update.AppUpdateInfo
import com.pichitube.app.core.update.UpdateManager
import com.pichitube.app.core.update.UpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val historyDao: HistoryDao,
    private val updateManager: UpdateManager,
) : ViewModel() {

    val prefs: StateFlow<AppPreferences> = prefsRepository.preferences
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppPreferences())

    val updateState: StateFlow<UpdateUiState> = updateManager.state

    fun setThemeMode(mode: ThemeMode) = viewModelScope.launch { prefsRepository.setThemeMode(mode) }
    fun setDefaultQuality(q: VideoQuality) = viewModelScope.launch { prefsRepository.setDefaultQuality(q) }
    fun setSponsorBlock(enabled: Boolean) = viewModelScope.launch { prefsRepository.setSponsorBlock(enabled) }
    fun setShowShorts(show: Boolean) = viewModelScope.launch { prefsRepository.setShowShorts(show) }
    fun setHardwareDecoder(enabled: Boolean) = viewModelScope.launch { prefsRepository.setHardwareDecoder(enabled) }
    fun setContentRegion(region: String) = viewModelScope.launch { prefsRepository.setContentRegion(region) }
    fun clearHistory() = viewModelScope.launch { historyDao.deleteAll() }
    fun clearSearchHistory() = viewModelScope.launch { prefsRepository.clearSearchHistory() }

    fun checkForUpdates() = updateManager.checkForUpdates(isManualCheck = true)
    fun startDownload(info: AppUpdateInfo) = updateManager.startDownload(info)
    fun installApk(file: File) = updateManager.installApk(file)
    fun dismissUpdate() = updateManager.dismiss()
}
