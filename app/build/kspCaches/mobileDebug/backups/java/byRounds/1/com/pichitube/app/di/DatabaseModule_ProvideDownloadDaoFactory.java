package com.pichitube.app.di;

import com.pichitube.app.core.data.db.PichiTubeDatabase;
import com.pichitube.app.core.data.db.dao.DownloadDao;
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
public final class DatabaseModule_ProvideDownloadDaoFactory implements Factory<DownloadDao> {
  private final Provider<PichiTubeDatabase> dbProvider;

  public DatabaseModule_ProvideDownloadDaoFactory(Provider<PichiTubeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DownloadDao get() {
    return provideDownloadDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideDownloadDaoFactory create(
      javax.inject.Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideDownloadDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideDownloadDaoFactory create(
      Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideDownloadDaoFactory(dbProvider);
  }

  public static DownloadDao provideDownloadDao(PichiTubeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDownloadDao(db));
  }
}
