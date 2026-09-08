package com.pichitube.app.di;

import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.InnerTubeService;
import com.pichitube.app.core.network.PichiTubeDownloader;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class ExtractorModule_ProvideExtractorServiceFactory implements Factory<ExtractorService> {
  private final Provider<PichiTubeDownloader> downloaderProvider;

  private final Provider<InnerTubeService> innerTubeServiceProvider;

  public ExtractorModule_ProvideExtractorServiceFactory(
      Provider<PichiTubeDownloader> downloaderProvider,
      Provider<InnerTubeService> innerTubeServiceProvider) {
    this.downloaderProvider = downloaderProvider;
    this.innerTubeServiceProvider = innerTubeServiceProvider;
  }

  @Override
  public ExtractorService get() {
    return provideExtractorService(downloaderProvider.get(), innerTubeServiceProvider.get());
  }

  public static ExtractorModule_ProvideExtractorServiceFactory create(
      javax.inject.Provider<PichiTubeDownloader> downloaderProvider,
      javax.inject.Provider<InnerTubeService> innerTubeServiceProvider) {
    return new ExtractorModule_ProvideExtractorServiceFactory(Providers.asDaggerProvider(downloaderProvider), Providers.asDaggerProvider(innerTubeServiceProvider));
  }

  public static ExtractorModule_ProvideExtractorServiceFactory create(
      Provider<PichiTubeDownloader> downloaderProvider,
      Provider<InnerTubeService> innerTubeServiceProvider) {
    return new ExtractorModule_ProvideExtractorServiceFactory(downloaderProvider, innerTubeServiceProvider);
  }

  public static ExtractorService provideExtractorService(PichiTubeDownloader downloader,
      InnerTubeService innerTubeService) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideExtractorService(downloader, innerTubeService));
  }
}
