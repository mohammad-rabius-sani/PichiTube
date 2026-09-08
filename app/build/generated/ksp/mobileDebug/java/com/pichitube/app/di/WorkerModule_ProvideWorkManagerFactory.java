package com.pichitube.app.di;

import android.content.Context;
import androidx.work.Configuration;
import androidx.work.WorkManager;
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
public final class WorkerModule_ProvideWorkManagerFactory implements Factory<WorkManager> {
  private final Provider<Context> contextProvider;

  private final Provider<Configuration> configurationProvider;

  public WorkerModule_ProvideWorkManagerFactory(Provider<Context> contextProvider,
      Provider<Configuration> configurationProvider) {
    this.contextProvider = contextProvider;
    this.configurationProvider = configurationProvider;
  }

  @Override
  public WorkManager get() {
    return provideWorkManager(contextProvider.get(), configurationProvider.get());
  }

  public static WorkerModule_ProvideWorkManagerFactory create(
      javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<Configuration> configurationProvider) {
    return new WorkerModule_ProvideWorkManagerFactory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(configurationProvider));
  }

  public static WorkerModule_ProvideWorkManagerFactory create(Provider<Context> contextProvider,
      Provider<Configuration> configurationProvider) {
    return new WorkerModule_ProvideWorkManagerFactory(contextProvider, configurationProvider);
  }

  public static WorkManager provideWorkManager(Context context, Configuration configuration) {
    return Preconditions.checkNotNullFromProvides(WorkerModule.INSTANCE.provideWorkManager(context, configuration));
  }
}
