package com.pichitube.app;

import com.pichitube.app.core.data.prefs.PrefsRepository;
import com.pichitube.app.feature.player.PlayerManager;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<PrefsRepository> prefsRepositoryProvider;

  private final Provider<PlayerManager> playerManagerProvider;

  public MainActivity_MembersInjector(Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<PlayerManager> playerManagerProvider) {
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.playerManagerProvider = playerManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<PlayerManager> playerManagerProvider) {
    return new MainActivity_MembersInjector(prefsRepositoryProvider, playerManagerProvider);
  }

  public static MembersInjector<MainActivity> create(
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<PlayerManager> playerManagerProvider) {
    return new MainActivity_MembersInjector(Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(playerManagerProvider));
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPrefsRepository(instance, prefsRepositoryProvider.get());
    injectPlayerManager(instance, playerManagerProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.prefsRepository")
  public static void injectPrefsRepository(MainActivity instance, PrefsRepository prefsRepository) {
    instance.prefsRepository = prefsRepository;
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.playerManager")
  public static void injectPlayerManager(MainActivity instance, PlayerManager playerManager) {
    instance.playerManager = playerManager;
  }
}
