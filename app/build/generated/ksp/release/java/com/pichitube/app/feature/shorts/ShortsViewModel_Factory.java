package com.pichitube.app.feature.shorts;

import com.pichitube.app.core.data.prefs.PrefsRepository;
import com.pichitube.app.core.network.ExtractorService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class ShortsViewModel_Factory implements Factory<ShortsViewModel> {
  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<PrefsRepository> prefsRepositoryProvider;

  public ShortsViewModel_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<PrefsRepository> prefsRepositoryProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
  }

  @Override
  public ShortsViewModel get() {
    return newInstance(extractorServiceProvider.get(), prefsRepositoryProvider.get());
  }

  public static ShortsViewModel_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider) {
    return new ShortsViewModel_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(prefsRepositoryProvider));
  }

  public static ShortsViewModel_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<PrefsRepository> prefsRepositoryProvider) {
    return new ShortsViewModel_Factory(extractorServiceProvider, prefsRepositoryProvider);
  }

  public static ShortsViewModel newInstance(ExtractorService extractorService,
      PrefsRepository prefsRepository) {
    return new ShortsViewModel(extractorService, prefsRepository);
  }
}
