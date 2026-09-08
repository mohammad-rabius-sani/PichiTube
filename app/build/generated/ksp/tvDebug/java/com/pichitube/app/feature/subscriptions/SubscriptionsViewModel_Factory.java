package com.pichitube.app.feature.subscriptions;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.SubscriptionDao;
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
public final class SubscriptionsViewModel_Factory implements Factory<SubscriptionsViewModel> {
  private final Provider<SubscriptionDao> subscriptionDaoProvider;

  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public SubscriptionsViewModel_Factory(Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<ExtractorService> extractorServiceProvider, Provider<HistoryDao> historyDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.subscriptionDaoProvider = subscriptionDaoProvider;
    this.extractorServiceProvider = extractorServiceProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public SubscriptionsViewModel get() {
    return newInstance(subscriptionDaoProvider.get(), extractorServiceProvider.get(), historyDaoProvider.get(), prefsRepoProvider.get());
  }

  public static SubscriptionsViewModel_Factory create(
      javax.inject.Provider<SubscriptionDao> subscriptionDaoProvider,
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new SubscriptionsViewModel_Factory(Providers.asDaggerProvider(subscriptionDaoProvider), Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static SubscriptionsViewModel_Factory create(
      Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<ExtractorService> extractorServiceProvider, Provider<HistoryDao> historyDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new SubscriptionsViewModel_Factory(subscriptionDaoProvider, extractorServiceProvider, historyDaoProvider, prefsRepoProvider);
  }

  public static SubscriptionsViewModel newInstance(SubscriptionDao subscriptionDao,
      ExtractorService extractorService, HistoryDao historyDao,
      AppPreferencesRepository prefsRepo) {
    return new SubscriptionsViewModel(subscriptionDao, extractorService, historyDao, prefsRepo);
  }
}
