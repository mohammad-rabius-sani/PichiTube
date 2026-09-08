package com.pichitube.app;

import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.feature.player.SharedPlaybackState;
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
  private final Provider<AppPreferencesRepository> prefsRepositoryProvider;

  private final Provider<SharedPlaybackState> sharedPlaybackStateProvider;

  public MainActivity_MembersInjector(Provider<AppPreferencesRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    this.prefsRepositoryProvider = prefsRepositoryProvider;
    this.sharedPlaybackStateProvider = sharedPlaybackStateProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<AppPreferencesRepository> prefsRepositoryProvider,
      Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new MainActivity_MembersInjector(prefsRepositoryProvider, sharedPlaybackStateProvider);
  }

  public static MembersInjector<MainActivity> create(
      javax.inject.Provider<AppPreferencesRepository> prefsRepositoryProvider,
      javax.inject.Provider<SharedPlaybackState> sharedPlaybackStateProvider) {
    return new MainActivity_MembersInjector(Providers.asDaggerProvider(prefsRepositoryProvider), Providers.asDaggerProvider(sharedPlaybackStateProvider));
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPrefsRepository(instance, prefsRepositoryProvider.get());
    injectSharedPlaybackState(instance, sharedPlaybackStateProvider.get());
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.prefsRepository")
  public static void injectPrefsRepository(MainActivity instance,
      AppPreferencesRepository prefsRepository) {
    instance.prefsRepository = prefsRepository;
  }

  @InjectedFieldSignature("com.pichitube.app.MainActivity.sharedPlaybackState")
  public static void injectSharedPlaybackState(MainActivity instance,
      SharedPlaybackState sharedPlaybackState) {
    instance.sharedPlaybackState = sharedPlaybackState;
  }
}
