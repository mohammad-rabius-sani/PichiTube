package com.pichitube.app.core.network;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ExtractorService_Factory implements Factory<ExtractorService> {
  private final Provider<PichiTubeDownloader> downloaderProvider;

  private final Provider<InnerTubeService> innerTubeServiceProvider;

  public ExtractorService_Factory(Provider<PichiTubeDownloader> downloaderProvider,
      Provider<InnerTubeService> innerTubeServiceProvider) {
    this.downloaderProvider = downloaderProvider;
    this.innerTubeServiceProvider = innerTubeServiceProvider;
  }

  @Override
  public ExtractorService get() {
    return newInstance(downloaderProvider.get(), innerTubeServiceProvider.get());
  }

  public static ExtractorService_Factory create(
      javax.inject.Provider<PichiTubeDownloader> downloaderProvider,
      javax.inject.Provider<InnerTubeService> innerTubeServiceProvider) {
    return new ExtractorService_Factory(Providers.asDaggerProvider(downloaderProvider), Providers.asDaggerProvider(innerTubeServiceProvider));
  }

  public static ExtractorService_Factory create(Provider<PichiTubeDownloader> downloaderProvider,
      Provider<InnerTubeService> innerTubeServiceProvider) {
    return new ExtractorService_Factory(downloaderProvider, innerTubeServiceProvider);
  }

  public static ExtractorService newInstance(PichiTubeDownloader downloader,
      InnerTubeService innerTubeService) {
    return new ExtractorService(downloader, innerTubeService);
  }
}
