package com.pichitube.app.feature.player;

import android.content.Context;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.WatchLaterDao;
import com.pichitube.app.core.data.prefs.PrefsRepository;
import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.SponsorBlockService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class PlayerManager_Factory implements Factory<PlayerManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<SponsorBlockService> sponsorBlockServiceProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<WatchLaterDao> watchLaterDaoProvider;

  private final Provider<PrefsRepository> prefsRepositoryProvider;

  private final Provider<SharedPlaybackState> sharedPlaybackStateProvider;

  public PlayerManager_Factory(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<WatchLaterDao> watchLaterDaoProvider,
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    this.contextProvider = contextProvider;
    this.extractorServiceProvider = extractorServiceProvider;
    this.sponsorBlockServiceProvider = sponsorBlockServiceProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.watchLaterDaoProvider = watchLaterDaoProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.sharedPlaybackStateProvider = sharedPlaybackStateProvider;
  }

  @Override
  public PlayerManager get() {
    return newInstance(contextProvider.get(), extractorServiceProvider.get(), sponsorBlockServiceProvider.get(), historyDaoProvider.get(), watchLaterDaoProvider.get(), prefsRepositoryProvider.get(), sharedPlaybackStateProvider.get());
  }

  public static PlayerManager_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<SponsorBlockService> sponsorBlockServiceProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<WatchLaterDao> watchLaterDaoProvider,
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new PlayerManager_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(sponsorBlockServiceProvider), Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(watchLaterDaoProvider), Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(sharedPlaybackStateProvider));
  }

  public static PlayerManager_Factory create(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<WatchLaterDao> watchLaterDaoProvider,
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new PlayerManager_Factory(contextProvider, extractorServiceProvider, sponsorBlockServiceProvider, historyDaoProvider, watchLaterDaoProvider, prefsRepositoryProvider, sharedPlaybackStateProvider);
  }

  public static PlayerManager newInstance(Context context, ExtractorService extractorService,
      SponsorBlockService sponsorBlockService, HistoryDao historyDao, WatchLaterDao watchLaterDao,
      PrefsRepository prefsRepository, SharedPlaybackState sharedPlaybackState) {
    return new PlayerManager(context, extractorService, sponsorBlockService, historyDao, watchLaterDao, prefsRepository, sharedPlaybackState);
  }
}
