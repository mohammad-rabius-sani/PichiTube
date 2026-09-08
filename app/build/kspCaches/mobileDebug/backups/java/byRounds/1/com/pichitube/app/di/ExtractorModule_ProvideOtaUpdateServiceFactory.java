package com.pichitube.app.di;

import com.pichitube.app.core.network.OtaUpdateService;
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
public final class ExtractorModule_ProvideOtaUpdateServiceFactory implements Factory<OtaUpdateService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public ExtractorModule_ProvideOtaUpdateServiceFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public OtaUpdateService get() {
    return provideOtaUpdateService(okHttpClientProvider.get());
  }

  public static ExtractorModule_ProvideOtaUpdateServiceFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideOtaUpdateServiceFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static ExtractorModule_ProvideOtaUpdateServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideOtaUpdateServiceFactory(okHttpClientProvider);
  }

  public static OtaUpdateService provideOtaUpdateService(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideOtaUpdateService(okHttpClient));
  }
}
