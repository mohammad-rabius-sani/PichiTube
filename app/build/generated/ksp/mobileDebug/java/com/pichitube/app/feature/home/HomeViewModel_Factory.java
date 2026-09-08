package com.pichitube.app.feature.home;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.network.DeArrowService;
import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.InnerTubeService;
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

  private final Provider<InnerTubeService> innerTubeServiceProvider;

  private final Provider<DeArrowService> deArrowServiceProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  public HomeViewModel_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<DeArrowService> deArrowServiceProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<HistoryDao> historyDaoProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.innerTubeServiceProvider = innerTubeServiceProvider;
    this.deArrowServiceProvider = deArrowServiceProvider;
    this.prefsRepoProvider = prefsRepoProvider;
    this.historyDaoProvider = historyDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(extractorServiceProvider.get(), innerTubeServiceProvider.get(), deArrowServiceProvider.get(), prefsRepoProvider.get(), historyDaoProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<InnerTubeService> innerTubeServiceProvider,
      javax.inject.Provider<DeArrowService> deArrowServiceProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(innerTubeServiceProvider), Providers.asDaggerProvider(deArrowServiceProvider), Providers.asDaggerProvider(prefsRepoProvider), Providers.asDaggerProvider(historyDaoProvider));
  }

  public static HomeViewModel_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<DeArrowService> deArrowServiceProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<HistoryDao> historyDaoProvider) {
    return new HomeViewModel_Factory(extractorServiceProvider, innerTubeServiceProvider, deArrowServiceProvider, prefsRepoProvider, historyDaoProvider);
  }

  public static HomeViewModel newInstance(ExtractorService extractorService,
      InnerTubeService innerTubeService, DeArrowService deArrowService,
      AppPreferencesRepository prefsRepo, HistoryDao historyDao) {
    return new HomeViewModel(extractorService, innerTubeService, deArrowService, prefsRepo, historyDao);
  }
}
