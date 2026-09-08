package com.pichitube.app.feature.player;

import android.content.Context;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.BookmarkDao;
import com.pichitube.app.core.data.db.dao.DownloadDao;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.PlaylistDao;
import com.pichitube.app.core.data.db.dao.SponsorSegmentDao;
import com.pichitube.app.core.data.db.dao.SubscriptionDao;
import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.InnerTubeService;
import com.pichitube.app.core.network.ReturnYouTubeDislikeService;
import com.pichitube.app.core.network.SponsorBlockService;
import com.pichitube.app.feature.player.audio.AudioEqualizerManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class PlayerViewModel_Factory implements Factory<PlayerViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<InnerTubeService> innerTubeServiceProvider;

  private final Provider<SponsorBlockService> sponsorBlockServiceProvider;

  private final Provider<ReturnYouTubeDislikeService> rydServiceProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<SubscriptionDao> subscriptionDaoProvider;

  private final Provider<BookmarkDao> bookmarkDaoProvider;

  private final Provider<PlaylistDao> playlistDaoProvider;

  private final Provider<DownloadDao> downloadDaoProvider;

  private final Provider<SponsorSegmentDao> sponsorSegmentDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  private final Provider<PlaybackManager> playbackManagerProvider;

  private final Provider<AudioEqualizerManager> equalizerManagerProvider;

  public PlayerViewModel_Factory(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<DownloadDao> downloadDaoProvider,
      Provider<SponsorSegmentDao> sponsorSegmentDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<PlaybackManager> playbackManagerProvider,
      Provider<AudioEqualizerManager> equalizerManagerProvider) {
    this.contextProvider = contextProvider;
    this.extractorServiceProvider = extractorServiceProvider;
    this.innerTubeServiceProvider = innerTubeServiceProvider;
    this.sponsorBlockServiceProvider = sponsorBlockServiceProvider;
    this.rydServiceProvider = rydServiceProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.subscriptionDaoProvider = subscriptionDaoProvider;
    this.bookmarkDaoProvider = bookmarkDaoProvider;
    this.playlistDaoProvider = playlistDaoProvider;
    this.downloadDaoProvider = downloadDaoProvider;
    this.sponsorSegmentDaoProvider = sponsorSegmentDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
    this.playbackManagerProvider = playbackManagerProvider;
    this.equalizerManagerProvider = equalizerManagerProvider;
  }

  @Override
  public PlayerViewModel get() {
    return newInstance(contextProvider.get(), extractorServiceProvider.get(), innerTubeServiceProvider.get(), sponsorBlockServiceProvider.get(), rydServiceProvider.get(), historyDaoProvider.get(), subscriptionDaoProvider.get(), bookmarkDaoProvider.get(), playlistDaoProvider.get(), downloadDaoProvider.get(), sponsorSegmentDaoProvider.get(), prefsRepoProvider.get(), playbackManagerProvider.get(), equalizerManagerProvider.get());
  }

  public static PlayerViewModel_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<InnerTubeService> innerTubeServiceProvider,
      javax.inject.Provider<SponsorBlockService> sponsorBlockServiceProvider,
      javax.inject.Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<SubscriptionDao> subscriptionDaoProvider,
      javax.inject.Provider<BookmarkDao> bookmarkDaoProvider,
      javax.inject.Provider<PlaylistDao> playlistDaoProvider,
      javax.inject.Provider<DownloadDao> downloadDaoProvider,
      javax.inject.Provider<SponsorSegmentDao> sponsorSegmentDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider,
      javax.inject.Provider<PlaybackManager> playbackManagerProvider,
      javax.inject.Provider<AudioEqualizerManager> equalizerManagerProvider) {
    return new PlayerViewModel_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(innerTubeServiceProvider), Providers.asDaggerProvider(sponsorBlockServiceProvider), Providers.asDaggerProvider(rydServiceProvider), Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(subscriptionDaoProvider), Providers.asDaggerProvider(bookmarkDaoProvider), Providers.asDaggerProvider(playlistDaoProvider), Providers.asDaggerProvider(downloadDaoProvider), Providers.asDaggerProvider(sponsorSegmentDaoProvider), Providers.asDaggerProvider(prefsRepoProvider), Providers.asDaggerProvider(playbackManagerProvider), Providers.asDaggerProvider(equalizerManagerProvider));
  }

  public static PlayerViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<SponsorBlockService> sponsorBlockServiceProvider,
      Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      Provider<HistoryDao> historyDaoProvider, Provider<SubscriptionDao> subscriptionDaoProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<DownloadDao> downloadDaoProvider,
      Provider<SponsorSegmentDao> sponsorSegmentDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<PlaybackManager> playbackManagerProvider,
      Provider<AudioEqualizerManager> equalizerManagerProvider) {
    return new PlayerViewModel_Factory(contextProvider, extractorServiceProvider, innerTubeServiceProvider, sponsorBlockServiceProvider, rydServiceProvider, historyDaoProvider, subscriptionDaoProvider, bookmarkDaoProvider, playlistDaoProvider, downloadDaoProvider, sponsorSegmentDaoProvider, prefsRepoProvider, playbackManagerProvider, equalizerManagerProvider);
  }

  public static PlayerViewModel newInstance(Context context, ExtractorService extractorService,
      InnerTubeService innerTubeService, SponsorBlockService sponsorBlockService,
      ReturnYouTubeDislikeService rydService, HistoryDao historyDao,
      SubscriptionDao subscriptionDao, BookmarkDao bookmarkDao, PlaylistDao playlistDao,
      DownloadDao downloadDao, SponsorSegmentDao sponsorSegmentDao,
      AppPreferencesRepository prefsRepo, PlaybackManager playbackManager,
      AudioEqualizerManager equalizerManager) {
    return new PlayerViewModel(context, extractorService, innerTubeService, sponsorBlockService, rydService, historyDao, subscriptionDao, bookmarkDao, playlistDao, downloadDao, sponsorSegmentDao, prefsRepo, playbackManager, equalizerManager);
  }
}
