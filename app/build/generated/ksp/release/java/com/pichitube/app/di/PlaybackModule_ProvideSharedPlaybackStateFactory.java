package com.pichitube.app.di;

import com.pichitube.app.feature.player.SharedPlaybackState;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class PlaybackModule_ProvideSharedPlaybackStateFactory implements Factory<SharedPlaybackState> {
  @Override
  public SharedPlaybackState get() {
    return provideSharedPlaybackState();
  }

  public static PlaybackModule_ProvideSharedPlaybackStateFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SharedPlaybackState provideSharedPlaybackState() {
    return Preconditions.checkNotNullFromProvides(PlaybackModule.INSTANCE.provideSharedPlaybackState());
  }

  private static final class InstanceHolder {
    static final PlaybackModule_ProvideSharedPlaybackStateFactory INSTANCE = new PlaybackModule_ProvideSharedPlaybackStateFactory();
  }
}
