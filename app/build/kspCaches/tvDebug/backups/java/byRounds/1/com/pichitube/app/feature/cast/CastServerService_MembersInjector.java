package com.pichitube.app.feature.cast;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class CastServerService_MembersInjector implements MembersInjector<CastServerService> {
  private final Provider<AppPreferencesRepository> prefsRepoProvider;

  public CastServerService_MembersInjector(Provider<AppPreferencesRepository> prefsRepoProvider) {
    this.prefsRepoProvider = prefsRepoProvider;
  }

  public static MembersInjector<CastServerService> create(
      Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new CastServerService_MembersInjector(prefsRepoProvider);
  }

  public static MembersInjector<CastServerService> create(
      javax.inject.Provider<AppPreferencesRepository> prefsRepoProvider) {
    return new CastServerService_MembersInjector(Providers.asDaggerProvider(prefsRepoProvider));
  }

  @Override
  public void injectMembers(CastServerService instance) {
    injectPrefsRepo(instance, prefsRepoProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.feature.cast.CastServerService.prefsRepo")
  public static void injectPrefsRepo(CastServerService instance,
      AppPreferencesRepository prefsRepo) {
    instance.prefsRepo = prefsRepo;
  }
}
