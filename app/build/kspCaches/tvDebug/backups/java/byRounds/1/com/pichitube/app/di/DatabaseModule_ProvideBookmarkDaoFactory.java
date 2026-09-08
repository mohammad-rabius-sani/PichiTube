package com.pichitube.app.di;

import com.pichitube.app.core.data.db.PichiTubeDatabase;
import com.pichitube.app.core.data.db.dao.BookmarkDao;
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
public final class DatabaseModule_ProvideBookmarkDaoFactory implements Factory<BookmarkDao> {
  private final Provider<PichiTubeDatabase> dbProvider;

  public DatabaseModule_ProvideBookmarkDaoFactory(Provider<PichiTubeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BookmarkDao get() {
    return provideBookmarkDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBookmarkDaoFactory create(
      javax.inject.Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideBookmarkDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideBookmarkDaoFactory create(
      Provider<PichiTubeDatabase> dbProvider) {
    return new DatabaseModule_ProvideBookmarkDaoFactory(dbProvider);
  }

  public static BookmarkDao provideBookmarkDao(PichiTubeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBookmarkDao(db));
  }
}
