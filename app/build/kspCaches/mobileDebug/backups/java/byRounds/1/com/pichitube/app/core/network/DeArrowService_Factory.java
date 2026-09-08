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
public final class DeArrowService_Factory implements Factory<DeArrowService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public DeArrowService_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public DeArrowService get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static DeArrowService_Factory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new DeArrowService_Factory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static DeArrowService_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new DeArrowService_Factory(okHttpClientProvider);
  }

  public static DeArrowService newInstance(OkHttpClient okHttpClient) {
    return new DeArrowService(okHttpClient);
  }
}
