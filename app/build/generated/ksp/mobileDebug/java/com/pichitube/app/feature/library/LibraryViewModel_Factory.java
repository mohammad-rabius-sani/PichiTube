package com.pichitube.app.feature.library;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.BookmarkDao;
import com.pichitube.app.core.data.db.dao.DownloadDao;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.PlaylistDao;
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
public final class LibraryViewModel_Factory implements Factory<LibraryViewModel> {
  private final Provider<HistoryDao> historyDaoProvider;

  private final Provider<BookmarkDao> bookmarkDaoProvider;

  private final Provider<PlaylistDao> playlistDaoProvider;

  private final Provider<DownloadDao> downloadDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public LibraryViewModel_Factory(Provider<HistoryDao> historyDaoProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<DownloadDao> downloadDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.historyDaoProvider = historyDaoProvider;
    this.bookmarkDaoProvider = bookmarkDaoProvider;
    this.playlistDaoProvider = playlistDaoProvider;
    this.downloadDaoProvider = downloadDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(historyDaoProvider.get(), bookmarkDaoProvider.get(), playlistDaoProvider.get(), downloadDaoProvider.get(), prefsRepoProvider.get());
  }

  public static LibraryViewModel_Factory create(
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<BookmarkDao> bookmarkDaoProvider,
      javax.inject.Provider<PlaylistDao> playlistDaoProvider,
      javax.inject.Provider<DownloadDao> downloadDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new LibraryViewModel_Factory(Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(bookmarkDaoProvider), Providers.asDaggerProvider(playlistDaoProvider), Providers.asDaggerProvider(downloadDaoProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static LibraryViewModel_Factory create(Provider<HistoryDao> historyDaoProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<PlaylistDao> playlistDaoProvider,
      Provider<DownloadDao> downloadDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new LibraryViewModel_Factory(historyDaoProvider, bookmarkDaoProvider, playlistDaoProvider, downloadDaoProvider, prefsRepoProvider);
  }

  public static LibraryViewModel newInstance(HistoryDao historyDao, BookmarkDao bookmarkDao,
      PlaylistDao playlistDao, DownloadDao downloadDao, AppPreferencesRepository prefsRepo) {
    return new LibraryViewModel(historyDao, bookmarkDao, playlistDao, downloadDao, prefsRepo);
  }
}
