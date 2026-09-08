package com.pichitube.app.feature.player;

import androidx.lifecycle.SavedStateHandle;
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
public final class PlayerViewModel_Factory implements Factory<PlayerViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<SponsorBlockService> sponsorBlockServiceProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<WatchLaterDao> watchLaterDaoProvider;

  private final Provider<PrefsRepository> prefsRepositoryProvider;

  private final Provider<SharedPlaybackState> sharedPlaybackStateProvider;

  public PlayerViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<WatchLaterDao> watchLaterDaoProvider,
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.extractorServiceProvider = extractorServiceProvider;
    this.sponsorBlockServiceProvider = sponsorBlockServiceProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.watchLaterDaoProvider = watchLaterDaoProvider;
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.sharedPlaybackStateProvider = sharedPlaybackStateProvider;
  }

  @Override
  public PlayerViewModel get() {
    return newInstance(savedStateHandleProvider.get(), extractorServiceProvider.get(), sponsorBlockServiceProvider.get(), historyDaoProvider.get(), watchLaterDaoProvider.get(), prefsRepositoryProvider.get(), sharedPlaybackStateProvider.get());
  }

  public static PlayerViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<SponsorBlockService> sponsorBlockServiceProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<WatchLaterDao> watchLaterDaoProvider,
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new PlayerViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(sponsorBlockServiceProvider), Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(watchLaterDaoProvider), Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(sharedPlaybackStateProvider));
  }

  public static PlayerViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<WatchLaterDao> watchLaterDaoProvider,
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new PlayerViewModel_Factory(savedStateHandleProvider, extractorServiceProvider, sponsorBlockServiceProvider, historyDaoProvider, watchLaterDaoProvider, prefsRepositoryProvider, sharedPlaybackStateProvider);
  }

  public static PlayerViewModel newInstance(SavedStateHandle savedStateHandle,
      ExtractorService extractorService, SponsorBlockService sponsorBlockService,
      HistoryDao historyDao, WatchLaterDao watchLaterDao, PrefsRepository prefsRepository,
      SharedPlaybackState sharedPlaybackState) {
    return new PlayerViewModel(savedStateHandle, extractorService, sponsorBlockService, historyDao, watchLaterDao, prefsRepository, sharedPlaybackState);
  }
}
