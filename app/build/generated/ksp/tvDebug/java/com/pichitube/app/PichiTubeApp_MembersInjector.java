package com.pichitube.app;

import androidx.hilt.work.HiltWorkerFactory;
import com.pichitube.app.core.network.PichiTubeDownloader;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class PichiTubeApp_MembersInjector implements MembersInjector<PichiTubeApp> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<PichiTubeDownloader> pichiTubeDownloaderProvider;

  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public PichiTubeApp_MembersInjector(Provider<OkHttpClient> okHttpClientProvider,
      Provider<PichiTubeDownloader> pichiTubeDownloaderProvider,
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.pichiTubeDownloaderProvider = pichiTubeDownloaderProvider;
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<PichiTubeApp> create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<PichiTubeDownloader> pichiTubeDownloaderProvider,
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new PichiTubeApp_MembersInjector(okHttpClientProvider, pichiTubeDownloaderProvider, workerFactoryProvider);
  }

  public static MembersInjector<PichiTubeApp> create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider,
      javax.inject.Provider<PichiTubeDownloader> pichiTubeDownloaderProvider,
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new PichiTubeApp_MembersInjector(Providers.asDaggerProvider(okHttpClientProvider), Providers.asDaggerProvider(pichiTubeDownloaderProvider), Providers.asDaggerProvider(workerFactoryProvider));
  }

  @Override
  public void injectMembers(PichiTubeApp instance) {
    injectOkHttpClient(instance, okHttpClientProvider.get());
    injectPichiTubeDownloader(instance, pichiTubeDownloaderProvider.get());
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.PichiTubeApp.okHttpClient")
  public static void injectOkHttpClient(PichiTubeApp instance, OkHttpClient okHttpClient) {
    instance.okHttpClient = okHttpClient;
  }

  @InjectedFieldSignature("com.pichitube.app.PichiTubeApp.pichiTubeDownloader")
  public static void injectPichiTubeDownloader(PichiTubeApp instance,
      PichiTubeDownloader pichiTubeDownloader) {
    instance.pichiTubeDownloader = pichiTubeDownloader;
  }

  @InjectedFieldSignature("com.pichitube.app.PichiTubeApp.workerFactory")
  public static void injectWorkerFactory(PichiTubeApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
