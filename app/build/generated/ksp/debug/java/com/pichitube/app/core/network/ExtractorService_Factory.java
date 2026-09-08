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
  private final Provider<NewPipeDownloader> downloaderProvider;

  public ExtractorService_Factory(Provider<NewPipeDownloader> downloaderProvider) {
    this.downloaderProvider = downloaderProvider;
  }

  @Override
  public ExtractorService get() {
    return newInstance(downloaderProvider.get());
  }

  public static ExtractorService_Factory create(
      javax.inject.Provider<NewPipeDownloader> downloaderProvider) {
    return new ExtractorService_Factory(Providers.asDaggerProvider(downloaderProvider));
  }

  public static ExtractorService_Factory create(Provider<NewPipeDownloader> downloaderProvider) {
    return new ExtractorService_Factory(downloaderProvider);
  }

  public static ExtractorService newInstance(NewPipeDownloader downloader) {
    return new ExtractorService(downloader);
  }
}
