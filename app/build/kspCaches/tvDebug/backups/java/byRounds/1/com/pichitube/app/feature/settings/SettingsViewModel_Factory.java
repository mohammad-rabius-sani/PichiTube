package com.pichitube.app.feature.settings;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.network.OtaUpdateService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  private final Provider<OtaUpdateService> otaUpdateServiceProvider;

  public SettingsViewModel_Factory(Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<OtaUpdateService> otaUpdateServiceProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
    this.otaUpdateServiceProvider = otaUpdateServiceProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(prefsRepoProvider.get(), otaUpdateServiceProvider.get());
  }

  public static SettingsViewModel_Factory create(
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider,
      javax.inject.Provider<OtaUpdateService> otaUpdateServiceProvider) {
    return new SettingsViewModel_Factory(Providers.asDaggerProvider(prefsRepoProvider), Providers.asDaggerProvider(otaUpdateServiceProvider));
  }

  public static SettingsViewModel_Factory create(
      Provider<AppPreferencesRepository> prefsRepoProvider,
      Provider<OtaUpdateService> otaUpdateServiceProvider) {
    return new SettingsViewModel_Factory(prefsRepoProvider, otaUpdateServiceProvider);
  }

  public static SettingsViewModel newInstance(AppPreferencesRepository prefsRepo,
      OtaUpdateService otaUpdateService) {
    return new SettingsViewModel(prefsRepo, otaUpdateService);
  }
}
