package com.pichitube.app.feature.library;

import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.WatchLaterDao;
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

  private final Provider<WatchLaterDao> watchLaterDaoProvider;

  public LibraryViewModel_Factory(Provider<HistoryDao> historyDaoProvider,
      Provider<WatchLaterDao> watchLaterDaoProvider) {
    this.historyDaoProvider = historyDaoProvider;
    this.watchLaterDaoProvider = watchLaterDaoProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(historyDaoProvider.get(), watchLaterDaoProvider.get());
  }

  public static LibraryViewModel_Factory create(
      javax.inject.Provider<HistoryDao> historyDaoProvider,
      javax.inject.Provider<WatchLaterDao> watchLaterDaoProvider) {
    return new LibraryViewModel_Factory(Providers.asDaggerProvider(historyDaoProvider), Providers.asDaggerProvider(watchLaterDaoProvider));
  }

  public static LibraryViewModel_Factory create(Provider<HistoryDao> historyDaoProvider,
      Provider<WatchLaterDao> watchLaterDaoProvider) {
    return new LibraryViewModel_Factory(historyDaoProvider, watchLaterDaoProvider);
  }

  public static LibraryViewModel newInstance(HistoryDao historyDao, WatchLaterDao watchLaterDao) {
    return new LibraryViewModel(historyDao, watchLaterDao);
  }
}
