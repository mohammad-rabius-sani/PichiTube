package com.pichitube.app.feature.home;

import com.pichitube.app.core.data.db.dao.HistoryDao;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<PrefsRepository> prefsRepositoryProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  public HomeViewModel_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<PrefsRepository> prefsRepositoryProvider, Provider<HistoryDao> historyDaoProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.historyDaoProvider = historyDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(extractorServiceProvider.get(), prefsRepositoryProvider.get(), historyDaoProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(historyDaoProvider));
  }

  public static HomeViewModel_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<PrefsRepository> prefsRepositoryProvider, Provider<HistoryDao> historyDaoProvider) {
    return new HomeViewModel_Factory(extractorServiceProvider, prefsRepositoryProvider, historyDaoProvider);
  }

  public static HomeViewModel newInstance(ExtractorService extractorService,
      PrefsRepository prefsRepository, HistoryDao historyDao) {
    return new HomeViewModel(extractorService, prefsRepository, historyDao);
  }
}
