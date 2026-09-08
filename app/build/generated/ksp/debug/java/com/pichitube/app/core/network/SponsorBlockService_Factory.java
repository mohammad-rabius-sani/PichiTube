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
public final class SponsorBlockService_Factory implements Factory<SponsorBlockService> {
  private final Provider<OkHttpClient> clientProvider;

  public SponsorBlockService_Factory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public SponsorBlockService get() {
    return newInstance(clientProvider.get());
  }

  public static SponsorBlockService_Factory create(
      javax.inject.Provider<OkHttpClient> clientProvider) {
    return new SponsorBlockService_Factory(Providers.asDaggerProvider(clientProvider));
  }

  public static SponsorBlockService_Factory create(Provider<OkHttpClient> clientProvider) {
    return new SponsorBlockService_Factory(clientProvider);
  }

  public static SponsorBlockService newInstance(OkHttpClient client) {
    return new SponsorBlockService(client);
  }
}
