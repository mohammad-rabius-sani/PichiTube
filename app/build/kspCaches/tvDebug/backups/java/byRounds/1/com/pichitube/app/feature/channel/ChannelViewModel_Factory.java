package com.pichitube.app.feature.channel;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
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
public final class ChannelViewModel_Factory implements Factory<ChannelViewModel> {
  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<SubscriptionDao> subscriptionDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public ChannelViewModel_Factory(Provider<ExtractorService> extractorServiceProvider,
      Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.extractorServiceProvider = extractorServiceProvider;
    this.subscriptionDaoProvider = subscriptionDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ChannelViewModel get() {
    return newInstance(extractorServiceProvider.get(), subscriptionDaoProvider.get(), prefsRepoProvider.get());
  }

  public static ChannelViewModel_Factory create(
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<SubscriptionDao> subscriptionDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ChannelViewModel_Factory(Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(subscriptionDaoProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static ChannelViewModel_Factory create(Provider<ExtractorService> extractorServiceProvider,
      Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ChannelViewModel_Factory(extractorServiceProvider, subscriptionDaoProvider, prefsRepoProvider);
  }

  public static ChannelViewModel newInstance(ExtractorService extractorService,
      SubscriptionDao subscriptionDao, AppPreferencesRepository prefsRepo) {
    return new ChannelViewModel(extractorService, subscriptionDao, prefsRepo);
  }
}
