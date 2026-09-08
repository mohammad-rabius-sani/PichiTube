package com.pichitube.app.core.update;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class UpdateManager_Factory implements Factory<UpdateManager> {
  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public UpdateManager_Factory(Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.contextProvider = contextProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public UpdateManager get() {
    return newInstance(contextProvider.get(), okHttpClientProvider.get());
  }

  public static UpdateManager_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new UpdateManager_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static UpdateManager_Factory create(Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new UpdateManager_Factory(contextProvider, okHttpClientProvider);
  }

  public static UpdateManager newInstance(Context context, OkHttpClient okHttpClient) {
    return new UpdateManager(context, okHttpClient);
  }
}
