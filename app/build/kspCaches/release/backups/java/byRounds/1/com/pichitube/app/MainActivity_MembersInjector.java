package com.pichitube.app;

import com.pichitube.app.core.data.prefs.PrefsRepository;
import com.pichitube.app.core.update.UpdateManager;
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

  private final Provider<UpdateManager> updateManagerProvider;

  public MainActivity_MembersInjector(Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<PlayerManager> playerManagerProvider,
      Provider<UpdateManager> updateManagerProvider) {
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.playerManagerProvider = playerManagerProvider;
    this.updateManagerProvider = updateManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PrefsRepository> prefsRepositoryProvider,
      Provider<PlayerManager> playerManagerProvider,
      Provider<UpdateManager> updateManagerProvider) {
    return new MainActivity_MembersInjector(prefsRepositoryProvider, playerManagerProvider, updateManagerProvider);
  }

  public static MembersInjector<MainActivity> create(
      javax.inject.Provider<PrefsRepository> prefsRepositoryProvider,
      javax.inject.Provider<PlayerManager> playerManagerProvider,
      javax.inject.Provider<UpdateManager> updateManagerProvider) {
    return new MainActivity_MembersInjector(Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(playerManagerProvider), Providers.asDaggerProvider(updateManagerProvider));
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPrefsRepository(instance, prefsRepositoryProvider.get());
    injectPlayerManager(instance, playerManagerProvider.get());
    injectUpdateManager(instance, updateManagerProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.prefsRepository")
  public static void injectPrefsRepository(MainActivity instance, PrefsRepository prefsRepository) {
    instance.prefsRepository = prefsRepository;
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.playerManager")
  public static void injectPlayerManager(MainActivity instance, PlayerManager playerManager) {
    instance.playerManager = playerManager;
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.updateManager")
  public static void injectUpdateManager(MainActivity instance, UpdateManager updateManager) {
    instance.updateManager = updateManager;
  }
}
