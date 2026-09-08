package com.pichitube.app.core.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pichitube_prefs")

enum class ThemeMode { AMOLED, DARK, LIGHT }
enum class VideoQuality { AUTO, Q2160P, Q1440P, Q1080P, Q720P, Q480P, Q360P }

data class RegionInfo(val code: String, val name: String, val flag: String)

val SUPPORTED_REGIONS = listOf(
    RegionInfo("BD", "Bangladesh", "🇧🇩"),
    RegionInfo("US", "United States", "🇺🇸"),
    RegionInfo("GB", "United Kingdom", "🇬🇧"),
    RegionInfo("IN", "India", "🇮🇳"),
    RegionInfo("CA", "Canada", "🇨🇦"),
    RegionInfo("AU", "Australia", "🇦🇺"),
    RegionInfo("JP", "Japan", "🇯🇵"),
    RegionInfo("DE", "Germany", "🇩🇪"),
    RegionInfo("FR", "France", "🇫🇷"),
    RegionInfo("PK", "Pakistan", "🇵🇰"),
    RegionInfo("GLOBAL", "Global", "🌐"),
)

data class AppPreferences(
    val themeMode: ThemeMode = ThemeMode.AMOLED,
    val defaultQuality: VideoQuality = VideoQuality.AUTO,
    val sponsorBlockEnabled: Boolean = false,
    val showShorts: Boolean = true,
    val hardwareDecoder: Boolean = true,
    val contentRegion: String = "BD",
    val searchHistory: List<String> = emptyList(),
)

@Singleton
class PrefsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DEFAULT_QUALITY = stringPreferencesKey("default_quality")
        val SPONSORBLOCK = booleanPreferencesKey("sponsorblock")
        val SHOW_SHORTS = booleanPreferencesKey("show_shorts")
        val HARDWARE_DECODER = booleanPreferencesKey("hardware_decoder")
        val CONTENT_REGION = stringPreferencesKey("content_region")
        val SEARCH_HISTORY = stringPreferencesKey("search_history")
    }

    val preferences: Flow<AppPreferences> = context.dataStore.data
        .catch { e -> Timber.e(e, "DataStore error"); emit(emptyPreferences()) }
        .map { prefs ->
            AppPreferences(
                themeMode = ThemeMode.valueOf(prefs[Keys.THEME_MODE] ?: ThemeMode.AMOLED.name),
                defaultQuality = VideoQuality.valueOf(prefs[Keys.DEFAULT_QUALITY] ?: VideoQuality.AUTO.name),
                sponsorBlockEnabled = prefs[Keys.SPONSORBLOCK] ?: false,
                showShorts = prefs[Keys.SHOW_SHORTS] ?: true,
                hardwareDecoder = prefs[Keys.HARDWARE_DECODER] ?: true,
                contentRegion = prefs[Keys.CONTENT_REGION] ?: "BD",
                searchHistory = prefs[Keys.SEARCH_HISTORY]
                    ?.split("|||")
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),
            )
        }

    suspend fun setThemeMode(mode: ThemeMode) = context.dataStore.edit {
        it[Keys.THEME_MODE] = mode.name
    }

    suspend fun setDefaultQuality(q: VideoQuality) = context.dataStore.edit {
        it[Keys.DEFAULT_QUALITY] = q.name
    }

    suspend fun setSponsorBlock(enabled: Boolean) = context.dataStore.edit {
        it[Keys.SPONSORBLOCK] = enabled
    }

    suspend fun setShowShorts(show: Boolean) = context.dataStore.edit {
        it[Keys.SHOW_SHORTS] = show
    }

    suspend fun setHardwareDecoder(enabled: Boolean) = context.dataStore.edit {
        it[Keys.HARDWARE_DECODER] = enabled
    }

    suspend fun setContentRegion(region: String) = context.dataStore.edit {
        it[Keys.CONTENT_REGION] = region
    }

    suspend fun addSearchQuery(query: String) = context.dataStore.edit { prefs ->
        val existing = prefs[Keys.SEARCH_HISTORY]
            ?.split("|||")
            ?.filter { it.isNotBlank() }
            ?: emptyList()
        val updated = (listOf(query) + existing.filter { it != query }).take(20)
        prefs[Keys.SEARCH_HISTORY] = updated.joinToString("|||")
    }

    suspend fun removeSearchQuery(query: String) = context.dataStore.edit { prefs ->
        val existing = prefs[Keys.SEARCH_HISTORY]
            ?.split("|||")
            ?.filter { it.isNotBlank() }
            ?: emptyList()
        val updated = existing.filter { it != query }
        prefs[Keys.SEARCH_HISTORY] = updated.joinToString("|||")
    }

    suspend fun clearSearchHistory() = context.dataStore.edit {
        it[Keys.SEARCH_HISTORY] = ""
    }
}
