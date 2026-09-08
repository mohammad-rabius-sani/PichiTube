package com.pichitube.app.di;

import android.content.Context;
import com.pichitube.app.core.data.db.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideDatabaseFactory implements Factory<AppDatabase> {
  private final Provider<Context> ctxProvider;

  public AppModule_ProvideDatabaseFactory(Provider<Context> ctxProvider) {
    this.ctxProvider = ctxProvider;
  }

  @Override
  public AppDatabase get() {
    return provideDatabase(ctxProvider.get());
  }

  public static AppModule_ProvideDatabaseFactory create(
      javax.inject.Provider<Context> ctxProvider) {
    return new AppModule_ProvideDatabaseFactory(Providers.asDaggerProvider(ctxProvider));
  }

  public static AppModule_ProvideDatabaseFactory create(Provider<Context> ctxProvider) {
    return new AppModule_ProvideDatabaseFactory(ctxProvider);
  }

  public static AppDatabase provideDatabase(Context ctx) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDatabase(ctx));
  }
}
