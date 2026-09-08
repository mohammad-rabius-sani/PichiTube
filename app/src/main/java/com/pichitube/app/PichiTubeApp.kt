package com.pichitube.app

import android.app.Application
import androidx.work.Configuration
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import com.pichitube.app.core.network.NewPipeDownloader
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.localization.ContentCountry
import org.schabi.newpipe.extractor.localization.Localization
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PichiTubeApp : Application(), Configuration.Provider {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var downloader: NewPipeDownloader

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize NewPipe Extractor with Bangladesh as default
        try {
            NewPipe.init(downloader, Localization("en", "BD"), ContentCountry("BD"))
        } catch (e: Exception) {
            NewPipe.init(downloader, Localization.DEFAULT)
        }

        // Configure Coil with our OkHttp client
        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient { okHttpClient }
            .crossfade(true)
            .build()
        Coil.setImageLoader(imageLoader)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
