package com.pichitube.app.feature.player;

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
public final class PlayerService_MembersInjector implements MembersInjector<PlayerService> {
  private final Provider<PlayerManager> playerManagerProvider;

  public PlayerService_MembersInjector(Provider<PlayerManager> playerManagerProvider) {
    this.playerManagerProvider = playerManagerProvider;
  }

  public static MembersInjector<PlayerService> create(
      Provider<PlayerManager> playerManagerProvider) {
    return new PlayerService_MembersInjector(playerManagerProvider);
  }

  public static MembersInjector<PlayerService> create(
      javax.inject.Provider<PlayerManager> playerManagerProvider) {
    return new PlayerService_MembersInjector(Providers.asDaggerProvider(playerManagerProvider));
  }

  @Override
  public void injectMembers(PlayerService instance) {
    injectPlayerManager(instance, playerManagerProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.feature.player.PlayerService.playerManager")
  public static void injectPlayerManager(PlayerService instance, PlayerManager playerManager) {
    instance.playerManager = playerManager;
  }
}
