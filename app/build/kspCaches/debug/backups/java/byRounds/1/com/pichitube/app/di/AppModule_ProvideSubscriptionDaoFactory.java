package com.pichitube.app.di;

import com.pichitube.app.core.data.db.AppDatabase;
import com.pichitube.app.core.data.db.dao.SubscriptionDao;
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
public final class AppModule_ProvideSubscriptionDaoFactory implements Factory<SubscriptionDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideSubscriptionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SubscriptionDao get() {
    return provideSubscriptionDao(dbProvider.get());
  }

  public static AppModule_ProvideSubscriptionDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideSubscriptionDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static AppModule_ProvideSubscriptionDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideSubscriptionDaoFactory(dbProvider);
  }

  public static SubscriptionDao provideSubscriptionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSubscriptionDao(db));
  }
}
