package com.pichitube.app.feature.search;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.network.DeArrowService;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<DeArrowService> deArrowServiceProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public SearchViewModel_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<DeArrowService> deArrowServiceProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.deArrowServiceProvider = deArrowServiceProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(extractorServiceProvider.get(), deArrowServiceProvider.get(), prefsRepoProvider.get());
  }

  public static SearchViewModel_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<DeArrowService> deArrowServiceProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new SearchViewModel_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(deArrowServiceProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static SearchViewModel_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<DeArrowService> deArrowServiceProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new SearchViewModel_Factory(extractorServiceProvider, deArrowServiceProvider, prefsRepoProvider);
  }

  public static SearchViewModel newInstance(ExtractorService extractorService,
      DeArrowService deArrowService, AppPreferencesRepository prefsRepo) {
    return new SearchViewModel(extractorService, deArrowService, prefsRepo);
  }
}
