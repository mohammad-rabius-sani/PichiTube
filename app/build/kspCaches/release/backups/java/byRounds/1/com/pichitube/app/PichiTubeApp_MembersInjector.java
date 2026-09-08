package com.pichitube.app;

import com.pichitube.app.core.network.NewPipeDownloader;
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

  private final Provider<NewPipeDownloader> downloaderProvider;

  public PichiTubeApp_MembersInjector(Provider<OkHttpClient> okHttpClientProvider,
      Provider<NewPipeDownloader> downloaderProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.downloaderProvider = downloaderProvider;
  }

  public static MembersInjector<PichiTubeApp> create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<NewPipeDownloader> downloaderProvider) {
    return new PichiTubeApp_MembersInjector(okHttpClientProvider, downloaderProvider);
  }

  public static MembersInjector<PichiTubeApp> create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider,
      javax.inject.Provider<NewPipeDownloader> downloaderProvider) {
    return new PichiTubeApp_MembersInjector(Providers.asDaggerProvider(okHttpClientProvider), Providers.asDaggerProvider(downloaderProvider));
  }

  @Override
  public void injectMembers(PichiTubeApp instance) {
    injectOkHttpClient(instance, okHttpClientProvider.get());
    injectDownloader(instance, downloaderProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.PichiTubeApp.okHttpClient")
  public static void injectOkHttpClient(PichiTubeApp instance, OkHttpClient okHttpClient) {
    instance.okHttpClient = okHttpClient;
  }

  @InjectedFieldSignature("com.pichitube.app.PichiTubeApp.downloader")
  public static void injectDownloader(PichiTubeApp instance, NewPipeDownloader downloader) {
    instance.downloader = downloader;
  }
}
