package com.pichitube.app.feature.player.audio;

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
public final class AudioEqualizerManager_Factory implements Factory<AudioEqualizerManager> {
  @Override
  public AudioEqualizerManager get() {
    return newInstance();
  }

  public static AudioEqualizerManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AudioEqualizerManager newInstance() {
    return new AudioEqualizerManager();
  }

  private static final class InstanceHolder {
    static final AudioEqualizerManager_Factory INSTANCE = new AudioEqualizerManager_Factory();
  }
}
