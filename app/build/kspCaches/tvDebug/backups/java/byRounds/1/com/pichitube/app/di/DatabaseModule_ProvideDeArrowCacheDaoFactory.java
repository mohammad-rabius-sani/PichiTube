package com.pichitube.app.di;

import com.pichitube.app.core.data.db.PichiTubeDatabase;
import com.pichitube.app.core.data.db.dao.DeArrowCacheDao;
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
public final class DatabaseModule_ProvideDeArrowCacheDaoFactory implements Factory<DeArrowCacheDao> {
  private final Provider<PichiTubeDatabase> dbProvider;

  public DatabaseModule_ProvideDeArrowCacheDaoFactory(Provider<PichiTubeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DeArrowCacheDao get() {
    return provideDeArrowCacheDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideDeArrowCacheDaoFactory create(
      javax.inject.Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideDeArrowCacheDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideDeArrowCacheDaoFactory create(
      Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideDeArrowCacheDaoFactory(dbProvider);
  }

  public static DeArrowCacheDao provideDeArrowCacheDao(PichiTubeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDeArrowCacheDao(db));
  }
}
