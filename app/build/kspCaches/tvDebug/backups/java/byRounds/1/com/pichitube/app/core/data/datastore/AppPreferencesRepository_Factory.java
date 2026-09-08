package com.pichitube.app.core.data.datastore;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AppPreferencesRepository_Factory implements Factory<AppPreferencesRepository> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public AppPreferencesRepository_Factory(Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public AppPreferencesRepository get() {
    return newInstance(dataStoreProvider.get());
  }

  public static AppPreferencesRepository_Factory create(
      javax.inject.Provider<DataStore<Preferences>> dataStoreProvider) {
    return new AppPreferencesRepository_Factory(Providers.asDaggerProvider(dataStoreProvider));
  }

  public static AppPreferencesRepository_Factory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new AppPreferencesRepository_Factory(dataStoreProvider);
  }

  public static AppPreferencesRepository newInstance(DataStore<Preferences> dataStore) {
    return new AppPreferencesRepository(dataStore);
  }
}
