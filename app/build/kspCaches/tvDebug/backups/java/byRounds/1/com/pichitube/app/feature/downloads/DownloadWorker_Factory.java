package com.pichitube.app.feature.downloads;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.pichitube.app.core.data.db.dao.DownloadDao;
import com.pichitube.app.core.network.ExtractorService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DownloadWorker_Factory {
  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<DownloadDao> downloadDaoProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public DownloadWorker_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<DownloadDao> downloadDaoProvider, Provider<OkHttpClient> okHttpClientProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.downloadDaoProvider = downloadDaoProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  public DownloadWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, extractorServiceProvider.get(), downloadDaoProvider.get(), okHttpClientProvider.get());
  }

  public static DownloadWorker_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<DownloadDao> downloadDaoProvider,
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new DownloadWorker_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(downloadDaoProvider), Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static DownloadWorker_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<DownloadDao> downloadDaoProvider, Provider<OkHttpClient> okHttpClientProvider) {
    return new DownloadWorker_Factory(extractorServiceProvider, downloadDaoProvider, okHttpClientProvider);
  }

  public static DownloadWorker newInstance(Context context, WorkerParameters workerParams,
      ExtractorService extractorService, DownloadDao downloadDao, OkHttpClient okHttpClient) {
    return new DownloadWorker(context, workerParams, extractorService, downloadDao, okHttpClient);
  }
}
