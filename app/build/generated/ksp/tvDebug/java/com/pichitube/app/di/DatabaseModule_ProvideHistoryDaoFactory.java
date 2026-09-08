package com.pichitube.app.di;

import com.pichitube.app.core.data.db.PichiTubeDatabase;
import com.pichitube.app.core.data.db.dao.HistoryDao;
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
public final class DatabaseModule_ProvideHistoryDaoFactory implements Factory<HistoryDao> {
  private final Provider<PichiTubeDatabase> dbProvider;

  public DatabaseModule_ProvideHistoryDaoFactory(Provider<PichiTubeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public HistoryDao get() {
    return provideHistoryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideHistoryDaoFactory create(
      javax.inject.Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideHistoryDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideHistoryDaoFactory create(
      Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideHistoryDaoFactory(dbProvider);
  }

  public static HistoryDao provideHistoryDao(PichiTubeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHistoryDao(db));
  }
}
