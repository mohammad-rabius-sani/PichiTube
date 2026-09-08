package com.pichitube.app.feature.shorts;

import android.content.Context;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.InnerTubeService;
import com.pichitube.app.core.network.ReturnYouTubeDislikeService;
import com.pichitube.app.feature.player.PlaybackManager;
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
public final class ShortsViewModel_Factory implements Factory<ShortsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ExtractorService> extractorServiceProvider;

  private final Provider<InnerTubeService> innerTubeServiceProvider;

  private final Provider<ReturnYouTubeDislikeService> rydServiceProvider;

  private final Provider<PlaybackManager> playbackManagerProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public ShortsViewModel_Factory(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      Provider<PlaybackManager> playbackManagerProvider, Provider<HistoryDao> historyDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.contextProvider = contextProvider;
    this.extractorServiceProvider = extractorServiceProvider;
    this.innerTubeServiceProvider = innerTubeServiceProvider;
    this.rydServiceProvider = rydServiceProvider;
    this.playbackManagerProvider = playbackManagerProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ShortsViewModel get() {
    return newInstance(contextProvider.get(), extractorServiceProvider.get(), innerTubeServiceProvider.get(), rydServiceProvider.get(), playbackManagerProvider.get(), historyDaoProvider.get(), prefsRepoProvider.get());
  }

  public static ShortsViewModel_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<ExtractorService> extractorServiceProvider,
      javax.inject.Provider<InnerTubeService> innerTubeServiceProvider,
      javax.inject.Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      javax.inject.Provider<PlaybackManager> playbackManagerProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ShortsViewModel_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(extractorServiceProvider), Providers.asDaggerProvider(innerTubeServiceProvider), Providers.asDaggerProvider(rydServiceProvider), Providers.asDaggerProvider(playbackManagerProvider), Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static ShortsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ExtractorService> extractorServiceProvider,
      Provider<InnerTubeService> innerTubeServiceProvider,
      Provider<ReturnYouTubeDislikeService> rydServiceProvider,
      Provider<PlaybackManager> playbackManagerProvider, Provider<HistoryDao> historyDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ShortsViewModel_Factory(contextProvider, extractorServiceProvider, innerTubeServiceProvider, rydServiceProvider, playbackManagerProvider, historyDaoProvider, prefsRepoProvider);
  }

  public static ShortsViewModel newInstance(Context context, ExtractorService extractorService,
      InnerTubeService innerTubeService, ReturnYouTubeDislikeService rydService,
      PlaybackManager playbackManager, HistoryDao historyDao, AppPreferencesRepository prefsRepo) {
    return new ShortsViewModel(context, extractorService, innerTubeService, rydService, playbackManager, historyDao, prefsRepo);
  }
}
