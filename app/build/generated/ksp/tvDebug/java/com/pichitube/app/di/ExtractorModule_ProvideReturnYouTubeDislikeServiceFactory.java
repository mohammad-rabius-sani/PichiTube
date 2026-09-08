package com.pichitube.app.di;

import com.pichitube.app.core.network.ReturnYouTubeDislikeService;
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
public final class ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory implements Factory<ReturnYouTubeDislikeService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public ReturnYouTubeDislikeService get() {
    return provideReturnYouTubeDislikeService(okHttpClientProvider.get());
  }

  public static ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory(okHttpClientProvider);
  }

  public static ReturnYouTubeDislikeService provideReturnYouTubeDislikeService(
      OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideReturnYouTubeDislikeService(okHttpClient));
  }
}
