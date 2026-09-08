package com.pichitube.app.di;

import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class WorkerModule_ProvideWorkManagerConfigurationFactory implements Factory<Configuration> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public WorkerModule_ProvideWorkManagerConfigurationFactory(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  @Override
  public Configuration get() {
    return provideWorkManagerConfiguration(workerFactoryProvider.get());
  }

  public static WorkerModule_ProvideWorkManagerConfigurationFactory create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WorkerModule_ProvideWorkManagerConfigurationFactory(Providers.asDaggerProvider(workerFactoryProvider));
  }

  public static WorkerModule_ProvideWorkManagerConfigurationFactory create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WorkerModule_ProvideWorkManagerConfigurationFactory(workerFactoryProvider);
  }

  public static Configuration provideWorkManagerConfiguration(HiltWorkerFactory workerFactory) {
    return Preconditions.checkNotNullFromProvides(WorkerModule.INSTANCE.provideWorkManagerConfiguration(workerFactory));
  }
}
