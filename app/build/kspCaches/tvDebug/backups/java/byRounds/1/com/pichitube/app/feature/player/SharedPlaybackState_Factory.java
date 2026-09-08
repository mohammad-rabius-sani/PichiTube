package com.pichitube.app.feature.player;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SharedPlaybackState_Factory implements Factory<SharedPlaybackState> {
  @Override
  public SharedPlaybackState get() {
    return newInstance();
  }

  public static SharedPlaybackState_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SharedPlaybackState newInstance() {
    return new SharedPlaybackState();
  }

  private static final class InstanceHolder {
    static final SharedPlaybackState_Factory INSTANCE = new SharedPlaybackState_Factory();
  }
}
