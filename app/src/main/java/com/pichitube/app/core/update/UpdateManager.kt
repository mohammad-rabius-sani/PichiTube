package com.pichitube.app.core.update

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.pichitube.app.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class AppUpdateInfo(
    val versionCode: Int = 1,
    val versionName: String = "1.0.0",
    val downloadUrl: String = "https://github.com/mohammad-rabius-sani/PichiTube/releases/latest/download/PichiTube.apk",
    val customMessage: String = "",
    val changelog: List<String> = emptyList(),
)

sealed class UpdateUiState {
    data object Idle : UpdateUiState()
    data object Checking : UpdateUiState()
    data class UpdateAvailable(val info: AppUpdateInfo) : UpdateUiState()
    data class Downloading(
        val info: AppUpdateInfo,
        val progress: Float,
        val downloadedMb: Float,
        val totalMb: Float,
    ) : UpdateUiState()
    data class ReadyToInstall(val info: AppUpdateInfo, val file: File) : UpdateUiState()
    data class UpToDate(val info: AppUpdateInfo) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

@Singleton
class UpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient,
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val _state = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val state: StateFlow<UpdateUiState> = _state.asStateFlow()

    private val updateManifestUrl =
        "https://raw.githubusercontent.com/mohammad-rabius-sani/PichiTube/main/update.json"

    fun checkForUpdates(isManualCheck: Boolean = false) {
        scope.launch {
            if (isManualCheck) _state.value = UpdateUiState.Checking

            runCatching {
                val request = Request.Builder()
                    .url(updateManifestUrl)
                    .header("Cache-Control", "no-cache")
                    .build()

                val response = okHttpClient.newCall(request).execute()
                if (!response.isSuccessful) {
                    if (isManualCheck) {
                        _state.value = UpdateUiState.Error("Failed to check for updates (HTTP ${response.code})")
                    }
                    return@launch
                }

                val body = response.body?.string().orEmpty()
                if (body.isBlank()) return@launch

                val info = json.decodeFromString<AppUpdateInfo>(body)
                val currentVersionCode = BuildConfig.VERSION_CODE

                if (info.versionCode > currentVersionCode) {
                    _state.value = UpdateUiState.UpdateAvailable(info)
                } else {
                    if (isManualCheck) {
                        _state.value = UpdateUiState.UpToDate(info)
                    }
                }
            }.onFailure { e ->
                Timber.e(e, "Check for update failed")
                if (isManualCheck) {
                    _state.value = UpdateUiState.Error(e.localizedMessage ?: "Could not connect to update server")
                }
            }
        }
    }

    fun startDownload(info: AppUpdateInfo) {
        scope.launch {
            runCatching {
                val apkDir = File(context.cacheDir, "apk").apply { mkdirs() }
                val targetFile = File(apkDir, "PichiTube-v${info.versionName}.apk")

                val request = Request.Builder()
                    .url(info.downloadUrl)
                    .build()

                val response = okHttpClient.newCall(request).execute()
                if (!response.isSuccessful) {
                    _state.value = UpdateUiState.Error("Download failed with HTTP ${response.code}")
                    return@launch
                }

                val responseBody = response.body ?: run {
                    _state.value = UpdateUiState.Error("Empty download response body")
                    return@launch
                }

                val contentLength = responseBody.contentLength().coerceAtLeast(1L)
                val totalMb = contentLength / (1024f * 1024f)

                val input = responseBody.byteStream()
                val output = FileOutputStream(targetFile)

                val buffer = ByteArray(8 * 1024)
                var bytesCopied = 0L
                var read: Int

                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                    bytesCopied += read
                    val progress = (bytesCopied.toFloat() / contentLength.toFloat()).coerceIn(0f, 1f)
                    val downloadedMb = bytesCopied / (1024f * 1024f)

                    _state.value = UpdateUiState.Downloading(
                        info = info,
                        progress = progress,
                        downloadedMb = downloadedMb,
                        totalMb = totalMb
                    )
                }

                output.flush()
                output.close()
                input.close()

                _state.value = UpdateUiState.ReadyToInstall(info, targetFile)

                withContext(Dispatchers.Main) {
                    installApk(targetFile)
                }
            }.onFailure { e ->
                Timber.e(e, "APK download failed")
                _state.value = UpdateUiState.Error("Download failed: ${e.localizedMessage}")
            }
        }
    }

    fun installApk(file: File) {
        try {
            val contentUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(installIntent)
        } catch (e: Exception) {
            Timber.e(e, "Failed to launch package installer")
            _state.value = UpdateUiState.Error("Failed to trigger installer: ${e.localizedMessage}")
        }
    }

    fun dismiss() {
        _state.value = UpdateUiState.Idle
    }
}
