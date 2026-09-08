package com.pichitube.app.feature.settings;

import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.prefs.PrefsRepository;
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
  private final Provider<PrefsRepository> prefsRepositoryProvider;

  private final Provider<HistoryDao> historyDaoProvider;

  public SettingsViewModel_Factory(Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<HistoryDao> historyDaoProvider) {
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.historyDaoProvider = historyDaoProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(prefsRepositoryProvider.get(), historyDaoProvider.get());
  }

  public static SettingsViewModel_Factory create(
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<HistoryDao> historyDaoProvider) {
    return new SettingsViewModel_Factory(Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(historyDaoProvider));
  }

  public static SettingsViewModel_Factory create(Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<HistoryDao> historyDaoProvider) {
    return new SettingsViewModel_Factory(prefsRepositoryProvider, historyDaoProvider);
  }

  public static SettingsViewModel newInstance(PrefsRepository prefsRepository,
      HistoryDao historyDao) {
    return new SettingsViewModel(prefsRepository, historyDao);
  }
}
