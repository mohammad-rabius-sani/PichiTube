package com.pichitube.app.feature.profiles;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.dao.UserProfileDao;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<UserProfileDao> userProfileDaoProvider;

  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public ProfileViewModel_Factory(Provider<UserProfileDao> userProfileDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.userProfileDaoProvider = userProfileDaoProvider;
    this.prefsRepoProvider = prefsRepoProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userProfileDaoProvider.get(), prefsRepoProvider.get());
  }

  public static ProfileViewModel_Factory create(
      javax.inject.Provider<UserProfileDao> userProfileDaoProvider,
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ProfileViewModel_Factory(Providers.asDaggerProvider(userProfileDaoProvider), Providers.asDaggerProvider(prefsRepoProvider));
  }

  public static ProfileViewModel_Factory create(Provider<UserProfileDao> userProfileDaoProvider,
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new ProfileViewModel_Factory(userProfileDaoProvider, prefsRepoProvider);
  }

  public static ProfileViewModel newInstance(UserProfileDao userProfileDao,
      AppPreferencesRepository prefsRepo) {
    return new ProfileViewModel(userProfileDao, prefsRepo);
  }
}
