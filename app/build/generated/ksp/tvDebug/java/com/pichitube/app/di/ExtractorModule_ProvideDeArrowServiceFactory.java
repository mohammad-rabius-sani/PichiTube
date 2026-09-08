package com.pichitube.app.di;

import com.pichitube.app.core.network.DeArrowService;
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
public final class ExtractorModule_ProvideDeArrowServiceFactory implements Factory<DeArrowService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public ExtractorModule_ProvideDeArrowServiceFactory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public DeArrowService get() {
    return provideDeArrowService(okHttpClientProvider.get());
  }

  public static ExtractorModule_ProvideDeArrowServiceFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideDeArrowServiceFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static ExtractorModule_ProvideDeArrowServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideDeArrowServiceFactory(okHttpClientProvider);
  }

  public static DeArrowService provideDeArrowService(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideDeArrowService(okHttpClient));
  }
}
