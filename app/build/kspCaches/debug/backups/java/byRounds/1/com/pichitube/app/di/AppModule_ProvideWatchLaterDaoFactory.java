package com.pichitube.app.di;

import com.pichitube.app.core.data.db.AppDatabase;
import com.pichitube.app.core.data.db.dao.WatchLaterDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideWatchLaterDaoFactory implements Factory<WatchLaterDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideWatchLaterDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public WatchLaterDao get() {
    return provideWatchLaterDao(dbProvider.get());
  }

  public static AppModule_ProvideWatchLaterDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideWatchLaterDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static AppModule_ProvideWatchLaterDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideWatchLaterDaoFactory(dbProvider);
  }

  public static WatchLaterDao provideWatchLaterDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWatchLaterDao(db));
  }
}
