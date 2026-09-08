package com.pichitube.app.di;

import android.content.Context;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.network.InnerTubeService;
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
public final class ExtractorModule_ProvideInnerTubeServiceFactory implements Factory<InnerTubeService> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  private final Provider<Context> contextProvider;

  public ExtractorModule_ProvideInnerTubeServiceFactory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider, Provider<Context> contextProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.prefsRepoProvider = prefsRepoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public InnerTubeService get() {
    return provideInnerTubeService(okHttpClientProvider.get(), prefsRepoProvider.get(), contextProvider.get());
  }

  public static ExtractorModule_ProvideInnerTubeServiceFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider,
      javax.inject.Provider<Context> contextProvider) {
    return new ExtractorModule_ProvideInnerTubeServiceFactory(Providers.asDaggerProvider(okHttpClientProvider), Providers.asDaggerProvider(prefsRepoProvider), Providers.asDaggerProvider(contextProvider));
  }

  public static ExtractorModule_ProvideInnerTubeServiceFactory create(
      Provider<OkHttpClient> okHttpClientProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider, Provider<Context> contextProvider) {
    return new ExtractorModule_ProvideInnerTubeServiceFactory(okHttpClientProvider, prefsRepoProvider, contextProvider);
  }

  public static InnerTubeService provideInnerTubeService(OkHttpClient okHttpClient,
      AppPreferencesRepository prefsRepo, Context context) {
    return Preconditions.checkNotNullFromProvides(ExtractorModule.INSTANCE.provideInnerTubeService(okHttpClient, prefsRepo, context));
  }
}
