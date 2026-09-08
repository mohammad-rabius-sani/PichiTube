package com.pichitube.app.feature.player;

import android.content.Context;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.HistoryDao;
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
public final class PlaybackManager_Factory implements Factory<PlaybackManager> {
  private final Provider<Context> contextProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  private final Provider<SharedPlaybackState> sharedPlaybackStateProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  public PlaybackManager_Factory(Provider<Context> contextProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider,
      Provider<HistoryDao> historyDaoProvider) {
    this.contextProvider = contextProvider;
    this.prefsRepoProvider = prefsRepoProvider;
    this.sharedPlaybackStateProvider = sharedPlaybackStateProvider;
    this.historyDaoProvider = historyDaoProvider;
  }

  @Override
  public PlaybackManager get() {
    return newInstance(contextProvider.get(), prefsRepoProvider.get(), sharedPlaybackStateProvider.get(), historyDaoProvider.get());
  }

  public static PlaybackManager_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider,
      javax.inject.Provider<SharedPlaybackState> sharedPlaybackStateProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider) {
    return new PlaybackManager_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(prefsRepoProvider), Providers.asDaggerProvider(sharedPlaybackStateProvider), Providers.asDaggerProvider(historyDaoProvider));
  }

  public static PlaybackManager_Factory create(Provider<Context> contextProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider,
      Provider<HistoryDao> historyDaoProvider) {
    return new PlaybackManager_Factory(contextProvider, prefsRepoProvider, sharedPlaybackStateProvider, historyDaoProvider);
  }

  public static PlaybackManager newInstance(Context context, AppPreferencesRepository prefsRepo,
      SharedPlaybackState sharedPlaybackState, HistoryDao historyDao) {
    return new PlaybackManager(context, prefsRepo, sharedPlaybackState, historyDao);
  }
}
