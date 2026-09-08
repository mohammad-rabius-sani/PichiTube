package com.pichitube.app.feature.downloads;

import androidx.work.WorkManager;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.DownloadDao;
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
public final class DownloadsViewModel_Factory implements Factory<DownloadsViewModel> {
  private final Provider<DownloadDao> downloadDaoProvider;

  private final Provider<WorkManager> workManagerProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public DownloadsViewModel_Factory(Provider<DownloadDao> downloadDaoProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.downloadDaoProvider = downloadDaoProvider;
    this.workManagerProvider = workManagerProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public DownloadsViewModel get() {
    return newInstance(downloadDaoProvider.get(), workManagerProvider.get(), prefsRepoProvider.get());
  }

  public static DownloadsViewModel_Factory create(
      javax.inject.Provider<DownloadDao> downloadDaoProvider,
      javax.inject.Provider<WorkManager> workManagerProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new DownloadsViewModel_Factory(Providers.asDaggerProvider(downloadDaoProvider), Providers.asDaggerProvider(workManagerProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static DownloadsViewModel_Factory create(Provider<DownloadDao> downloadDaoProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new DownloadsViewModel_Factory(downloadDaoProvider, workManagerProvider, prefsRepoProvider);
  }

  public static DownloadsViewModel newInstance(DownloadDao downloadDao, WorkManager workManager,
      AppPreferencesRepository prefsRepo) {
    return new DownloadsViewModel(downloadDao, workManager, prefsRepo);
  }
}
