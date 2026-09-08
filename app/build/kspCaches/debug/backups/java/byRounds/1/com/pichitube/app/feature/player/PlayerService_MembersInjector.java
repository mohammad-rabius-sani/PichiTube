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
  private final Provider<SharedPlaybackState> sharedStateProvider;

  public PlayerService_MembersInjector(Provider<SharedPlaybackState> sharedStateProvider) {
    this.sharedStateProvider = sharedStateProvider;
  }

  public static MembersInjector<PlayerService> create(
      Provider<SharedPlaybackState> sharedStateProvider) {
    return new PlayerService_MembersInjector(sharedStateProvider);
  }

  public static MembersInjector<PlayerService> create(
      javax.inject.Provider<SharedPlaybackState> sharedStateProvider) {
    return new PlayerService_MembersInjector(Providers.asDaggerProvider(sharedStateProvider));
  }

  @Override
  public void injectMembers(PlayerService instance) {
    injectSharedState(instance, sharedStateProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.feature.player.PlayerService.sharedState")
  public static void injectSharedState(PlayerService instance, SharedPlaybackState sharedState) {
    instance.sharedState = sharedState;
  }
}
