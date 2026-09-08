package com.pichitube.app.di;

import com.pichitube.app.core.network.PichiTubeDownloader;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvidePichiTubeDownloaderFactory implements Factory<PichiTubeDownloader> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvidePichiTubeDownloaderFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public PichiTubeDownloader get() {
    return providePichiTubeDownloader(okHttpClientProvider.get());
  }

  public static NetworkModule_ProvidePichiTubeDownloaderFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvidePichiTubeDownloaderFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static NetworkModule_ProvidePichiTubeDownloaderFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvidePichiTubeDownloaderFactory(okHttpClientProvider);
  }

  public static PichiTubeDownloader providePichiTubeDownloader(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.providePichiTubeDownloader(okHttpClient));
  }
}
