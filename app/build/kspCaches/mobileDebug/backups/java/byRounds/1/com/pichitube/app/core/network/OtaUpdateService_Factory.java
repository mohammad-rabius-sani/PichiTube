package com.pichitube.app.core.network;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class OtaUpdateService_Factory implements Factory<OtaUpdateService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public OtaUpdateService_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public OtaUpdateService get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static OtaUpdateService_Factory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new OtaUpdateService_Factory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static OtaUpdateService_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new OtaUpdateService_Factory(okHttpClientProvider);
  }

  public static OtaUpdateService newInstance(OkHttpClient okHttpClient) {
    return new OtaUpdateService(okHttpClient);
  }
}
