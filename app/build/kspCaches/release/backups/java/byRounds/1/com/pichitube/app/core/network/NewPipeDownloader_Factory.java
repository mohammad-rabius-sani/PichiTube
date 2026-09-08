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
public final class NewPipeDownloader_Factory implements Factory<NewPipeDownloader> {
  private final Provider<OkHttpClient> clientProvider;

  public NewPipeDownloader_Factory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public NewPipeDownloader get() {
    return newInstance(clientProvider.get());
  }

  public static NewPipeDownloader_Factory create(
      javax.inject.Provider<OkHttpClient> clientProvider) {
    return new NewPipeDownloader_Factory(Providers.asDaggerProvider(clientProvider));
  }

  public static NewPipeDownloader_Factory create(Provider<OkHttpClient> clientProvider) {
    return new NewPipeDownloader_Factory(clientProvider);
  }

  public static NewPipeDownloader newInstance(OkHttpClient client) {
    return new NewPipeDownloader(client);
  }
}
