package com.pichitube.app.di;

import com.pichitube.app.core.network.SponsorBlockService;
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
public final class ExtractorModule_ProvideSponsorBlockServiceFactory implements Factory<SponsorBlockService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public ExtractorModule_ProvideSponsorBlockServiceFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public SponsorBlockService get() {
    return provideSponsorBlockService(okHttpClientProvider.get());
  }

  public static ExtractorModule_ProvideSponsorBlockServiceFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideSponsorBlockServiceFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static ExtractorModule_ProvideSponsorBlockServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new ExtractorModule_ProvideSponsorBlockServiceFactory(okHttpClientProvider);
  }

  public static SponsorBlockService provideSponsorBlockService(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideSponsorBlockService(okHttpClient));
  }
}
