package com.pichitube.app.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
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
public final class DataStoreModule_ProvideAppPreferencesRepositoryFactory implements Factory<AppPreferencesRepository> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public DataStoreModule_ProvideAppPreferencesRepositoryFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public AppPreferencesRepository get() {
    return provideAppPreferencesRepository(dataStoreProvider.get());
  }

  public static DataStoreModule_ProvideAppPreferencesRepositoryFactory create(
      javax.inject.Provider<DataStore<Preferences>> dataStoreProvider) {
    return new DataStoreModule_ProvideAppPreferencesRepositoryFactory(Providers.asDaggerProvider(dataStoreProvider));
  }

  public static DataStoreModule_ProvideAppPreferencesRepositoryFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new DataStoreModule_ProvideAppPreferencesRepositoryFactory(dataStoreProvider);
  }

  public static AppPreferencesRepository provideAppPreferencesRepository(
      DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(DataStoreModule.INSTANCE.provideAppPreferencesRepository(dataStore));
  }
}
