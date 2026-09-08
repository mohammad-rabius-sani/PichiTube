package com.pichitube.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.Configuration;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.pichitube.app.core.data.datastore.AppPreferencesRepository;
import com.pichitube.app.core.data.db.PichiTubeDatabase;
import com.pichitube.app.core.data.db.dao.BookmarkDao;
import com.pichitube.app.core.data.db.dao.DownloadDao;
import com.pichitube.app.core.data.db.dao.HistoryDao;
import com.pichitube.app.core.data.db.dao.PlaylistDao;
import com.pichitube.app.core.data.db.dao.SponsorSegmentDao;
import com.pichitube.app.core.data.db.dao.SubscriptionDao;
import com.pichitube.app.core.data.db.dao.UserProfileDao;
import com.pichitube.app.core.network.DeArrowService;
import com.pichitube.app.core.network.ExtractorService;
import com.pichitube.app.core.network.InnerTubeService;
import com.pichitube.app.core.network.OtaUpdateService;
import com.pichitube.app.core.network.PichiTubeDownloader;
import com.pichitube.app.core.network.ReturnYouTubeDislikeService;
import com.pichitube.app.core.network.SponsorBlockService;
import com.pichitube.app.di.DataStoreModule_ProvideAppPreferencesRepositoryFactory;
import com.pichitube.app.di.DataStoreModule_ProvideDataStoreFactory;
import com.pichitube.app.di.DatabaseModule_ProvideBookmarkDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.pichitube.app.di.DatabaseModule_ProvideDownloadDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvideHistoryDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvidePlaylistDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvideSponsorSegmentDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvideSubscriptionDaoFactory;
import com.pichitube.app.di.DatabaseModule_ProvideUserProfileDaoFactory;
import com.pichitube.app.di.ExtractorModule_ProvideDeArrowServiceFactory;
import com.pichitube.app.di.ExtractorModule_ProvideExtractorServiceFactory;
import com.pichitube.app.di.ExtractorModule_ProvideInnerTubeServiceFactory;
import com.pichitube.app.di.ExtractorModule_ProvideOtaUpdateServiceFactory;
import com.pichitube.app.di.ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory;
import com.pichitube.app.di.ExtractorModule_ProvideSponsorBlockServiceFactory;
import com.pichitube.app.di.NetworkModule_ProvideOkHttpClientFactory;
import com.pichitube.app.di.NetworkModule_ProvidePichiTubeDownloaderFactory;
import com.pichitube.app.di.WorkerModule_ProvideWorkManagerConfigurationFactory;
import com.pichitube.app.di.WorkerModule_ProvideWorkManagerFactory;
import com.pichitube.app.feature.cast.CastServerService;
import com.pichitube.app.feature.cast.CastServerService_MembersInjector;
import com.pichitube.app.feature.channel.ChannelViewModel;
import com.pichitube.app.feature.channel.ChannelViewModel_HiltModules;
import com.pichitube.app.feature.channel.ChannelViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.channel.ChannelViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.downloads.DownloadWorker;
import com.pichitube.app.feature.downloads.DownloadWorker_AssistedFactory;
import com.pichitube.app.feature.downloads.DownloadsViewModel;
import com.pichitube.app.feature.downloads.DownloadsViewModel_HiltModules;
import com.pichitube.app.feature.downloads.DownloadsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.downloads.DownloadsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.home.HomeViewModel;
import com.pichitube.app.feature.home.HomeViewModel_HiltModules;
import com.pichitube.app.feature.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.library.LibraryViewModel;
import com.pichitube.app.feature.library.LibraryViewModel_HiltModules;
import com.pichitube.app.feature.library.LibraryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.library.LibraryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.player.PlaybackManager;
import com.pichitube.app.feature.player.PlayerService;
import com.pichitube.app.feature.player.PlayerViewModel;
import com.pichitube.app.feature.player.PlayerViewModel_HiltModules;
import com.pichitube.app.feature.player.PlayerViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.player.PlayerViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.player.SharedPlaybackState;
import com.pichitube.app.feature.player.audio.AudioEqualizerManager;
import com.pichitube.app.feature.profiles.ProfileViewModel;
import com.pichitube.app.feature.profiles.ProfileViewModel_HiltModules;
import com.pichitube.app.feature.profiles.ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.profiles.ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.search.SearchViewModel;
import com.pichitube.app.feature.search.SearchViewModel_HiltModules;
import com.pichitube.app.feature.search.SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.search.SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.settings.SettingsViewModel;
import com.pichitube.app.feature.settings.SettingsViewModel_HiltModules;
import com.pichitube.app.feature.settings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.settings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.shorts.ShortsViewModel;
import com.pichitube.app.feature.shorts.ShortsViewModel_HiltModules;
import com.pichitube.app.feature.shorts.ShortsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.shorts.ShortsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.pichitube.app.feature.subscriptions.SubscriptionsViewModel;
import com.pichitube.app.feature.subscriptions.SubscriptionsViewModel_HiltModules;
import com.pichitube.app.feature.subscriptions.SubscriptionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.pichitube.app.feature.subscriptions.SubscriptionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class DaggerPichiTubeApp_HiltComponents_SingletonC {
  private DaggerPichiTubeApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PichiTubeApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements PichiTubeApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PichiTubeApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PichiTubeApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PichiTubeApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PichiTubeApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PichiTubeApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PichiTubeApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PichiTubeApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PichiTubeApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PichiTubeApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PichiTubeApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PichiTubeApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(10).put(ChannelViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ChannelViewModel_HiltModules.KeyModule.provide()).put(DownloadsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, DownloadsViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(LibraryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, LibraryViewModel_HiltModules.KeyModule.provide()).put(PlayerViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlayerViewModel_HiltModules.KeyModule.provide()).put(ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileViewModel_HiltModules.KeyModule.provide()).put(SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SearchViewModel_HiltModules.KeyModule.provide()).put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide()).put(ShortsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ShortsViewModel_HiltModules.KeyModule.provide()).put(SubscriptionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SubscriptionsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPrefsRepository(instance, singletonCImpl.provideAppPreferencesRepositoryProvider.get());
      MainActivity_MembersInjector.injectSharedPlaybackState(instance, singletonCImpl.sharedPlaybackStateProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends PichiTubeApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ChannelViewModel> channelViewModelProvider;

    private Provider<DownloadsViewModel> downloadsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LibraryViewModel> libraryViewModelProvider;

    private Provider<PlayerViewModel> playerViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<ShortsViewModel> shortsViewModelProvider;

    private Provider<SubscriptionsViewModel> subscriptionsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.channelViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.downloadsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.libraryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.playerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.shortsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.subscriptionsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(10).put(ChannelViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) channelViewModelProvider)).put(DownloadsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) downloadsViewModelProvider)).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) homeViewModelProvider)).put(LibraryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) libraryViewModelProvider)).put(PlayerViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) playerViewModelProvider)).put(ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) profileViewModelProvider)).put(SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) searchViewModelProvider)).put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) settingsViewModelProvider)).put(ShortsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) shortsViewModelProvider)).put(SubscriptionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) subscriptionsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.pichitube.app.feature.channel.ChannelViewModel 
          return (T) new ChannelViewModel(singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.subscriptionDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 1: // com.pichitube.app.feature.downloads.DownloadsViewModel 
          return (T) new DownloadsViewModel(singletonCImpl.downloadDao(), singletonCImpl.provideWorkManagerProvider.get(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 2: // com.pichitube.app.feature.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.provideInnerTubeServiceProvider.get(), singletonCImpl.provideDeArrowServiceProvider.get(), singletonCImpl.provideAppPreferencesRepositoryProvider.get(), singletonCImpl.historyDao());

          case 3: // com.pichitube.app.feature.library.LibraryViewModel 
          return (T) new LibraryViewModel(singletonCImpl.historyDao(), singletonCImpl.bookmarkDao(), singletonCImpl.playlistDao(), singletonCImpl.downloadDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 4: // com.pichitube.app.feature.player.PlayerViewModel 
          return (T) new PlayerViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.provideInnerTubeServiceProvider.get(), singletonCImpl.provideSponsorBlockServiceProvider.get(), singletonCImpl.provideReturnYouTubeDislikeServiceProvider.get(), singletonCImpl.historyDao(), singletonCImpl.subscriptionDao(), singletonCImpl.bookmarkDao(), singletonCImpl.playlistDao(), singletonCImpl.downloadDao(), singletonCImpl.sponsorSegmentDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get(), singletonCImpl.playbackManagerProvider.get(), singletonCImpl.audioEqualizerManagerProvider.get());

          case 5: // com.pichitube.app.feature.profiles.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.userProfileDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 6: // com.pichitube.app.feature.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.provideDeArrowServiceProvider.get(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 7: // com.pichitube.app.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.provideAppPreferencesRepositoryProvider.get(), singletonCImpl.provideOtaUpdateServiceProvider.get());

          case 8: // com.pichitube.app.feature.shorts.ShortsViewModel 
          return (T) new ShortsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.provideInnerTubeServiceProvider.get(), singletonCImpl.provideReturnYouTubeDislikeServiceProvider.get(), singletonCImpl.playbackManagerProvider.get(), singletonCImpl.historyDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          case 9: // com.pichitube.app.feature.subscriptions.SubscriptionsViewModel 
          return (T) new SubscriptionsViewModel(singletonCImpl.subscriptionDao(), singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.historyDao(), singletonCImpl.provideAppPreferencesRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PichiTubeApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PichiTubeApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectCastServerService(CastServerService castServerService) {
      injectCastServerService2(castServerService);
    }

    @Override
    public void injectPlayerService(PlayerService playerService) {
    }

    private CastServerService injectCastServerService2(CastServerService instance) {
      CastServerService_MembersInjector.injectPrefsRepo(instance, singletonCImpl.provideAppPreferencesRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends PichiTubeApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<PichiTubeDownloader> providePichiTubeDownloaderProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<AppPreferencesRepository> provideAppPreferencesRepositoryProvider;

    private Provider<InnerTubeService> provideInnerTubeServiceProvider;

    private Provider<ExtractorService> provideExtractorServiceProvider;

    private Provider<PichiTubeDatabase> provideDatabaseProvider;

    private Provider<DownloadWorker_AssistedFactory> downloadWorker_AssistedFactoryProvider;

    private Provider<SharedPlaybackState> sharedPlaybackStateProvider;

    private Provider<Configuration> provideWorkManagerConfigurationProvider;

    private Provider<WorkManager> provideWorkManagerProvider;

    private Provider<DeArrowService> provideDeArrowServiceProvider;

    private Provider<SponsorBlockService> provideSponsorBlockServiceProvider;

    private Provider<ReturnYouTubeDislikeService> provideReturnYouTubeDislikeServiceProvider;

    private Provider<PlaybackManager> playbackManagerProvider;

    private Provider<AudioEqualizerManager> audioEqualizerManagerProvider;

    private Provider<OtaUpdateService> provideOtaUpdateServiceProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private DownloadDao downloadDao() {
      return DatabaseModule_ProvideDownloadDaoFactory.provideDownloadDao(provideDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.pichitube.app.feature.downloads.DownloadWorker", ((Provider) downloadWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private SubscriptionDao subscriptionDao() {
      return DatabaseModule_ProvideSubscriptionDaoFactory.provideSubscriptionDao(provideDatabaseProvider.get());
    }

    private HistoryDao historyDao() {
      return DatabaseModule_ProvideHistoryDaoFactory.provideHistoryDao(provideDatabaseProvider.get());
    }

    private BookmarkDao bookmarkDao() {
      return DatabaseModule_ProvideBookmarkDaoFactory.provideBookmarkDao(provideDatabaseProvider.get());
    }

    private PlaylistDao playlistDao() {
      return DatabaseModule_ProvidePlaylistDaoFactory.providePlaylistDao(provideDatabaseProvider.get());
    }

    private SponsorSegmentDao sponsorSegmentDao() {
      return DatabaseModule_ProvideSponsorSegmentDaoFactory.provideSponsorSegmentDao(provideDatabaseProvider.get());
    }

    private UserProfileDao userProfileDao() {
      return DatabaseModule_ProvideUserProfileDaoFactory.provideUserProfileDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 0));
      this.providePichiTubeDownloaderProvider = DoubleCheck.provider(new SwitchingProvider<PichiTubeDownloader>(singletonCImpl, 1));
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 6));
      this.provideAppPreferencesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AppPreferencesRepository>(singletonCImpl, 5));
      this.provideInnerTubeServiceProvider = DoubleCheck.provider(new SwitchingProvider<InnerTubeService>(singletonCImpl, 4));
      this.provideExtractorServiceProvider = DoubleCheck.provider(new SwitchingProvider<ExtractorService>(singletonCImpl, 3));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<PichiTubeDatabase>(singletonCImpl, 7));
      this.downloadWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<DownloadWorker_AssistedFactory>(singletonCImpl, 2));
      this.sharedPlaybackStateProvider = DoubleCheck.provider(new SwitchingProvider<SharedPlaybackState>(singletonCImpl, 8));
      this.provideWorkManagerConfigurationProvider = DoubleCheck.provider(new SwitchingProvider<Configuration>(singletonCImpl, 10));
      this.provideWorkManagerProvider = DoubleCheck.provider(new SwitchingProvider<WorkManager>(singletonCImpl, 9));
      this.provideDeArrowServiceProvider = DoubleCheck.provider(new SwitchingProvider<DeArrowService>(singletonCImpl, 11));
      this.provideSponsorBlockServiceProvider = DoubleCheck.provider(new SwitchingProvider<SponsorBlockService>(singletonCImpl, 12));
      this.provideReturnYouTubeDislikeServiceProvider = DoubleCheck.provider(new SwitchingProvider<ReturnYouTubeDislikeService>(singletonCImpl, 13));
      this.playbackManagerProvider = DoubleCheck.provider(new SwitchingProvider<PlaybackManager>(singletonCImpl, 14));
      this.audioEqualizerManagerProvider = DoubleCheck.provider(new SwitchingProvider<AudioEqualizerManager>(singletonCImpl, 15));
      this.provideOtaUpdateServiceProvider = DoubleCheck.provider(new SwitchingProvider<OtaUpdateService>(singletonCImpl, 16));
    }

    @Override
    public void injectPichiTubeApp(PichiTubeApp pichiTubeApp) {
      injectPichiTubeApp2(pichiTubeApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private PichiTubeApp injectPichiTubeApp2(PichiTubeApp instance) {
      PichiTubeApp_MembersInjector.injectOkHttpClient(instance, provideOkHttpClientProvider.get());
      PichiTubeApp_MembersInjector.injectPichiTubeDownloader(instance, providePichiTubeDownloaderProvider.get());
      PichiTubeApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.pichitube.app.core.network.PichiTubeDownloader 
          return (T) NetworkModule_ProvidePichiTubeDownloaderFactory.providePichiTubeDownloader(singletonCImpl.provideOkHttpClientProvider.get());

          case 2: // com.pichitube.app.feature.downloads.DownloadWorker_AssistedFactory 
          return (T) new DownloadWorker_AssistedFactory() {
            @Override
            public DownloadWorker create(Context context, WorkerParameters workerParams) {
              return new DownloadWorker(context, workerParams, singletonCImpl.provideExtractorServiceProvider.get(), singletonCImpl.downloadDao(), singletonCImpl.provideOkHttpClientProvider.get());
            }
          };

          case 3: // com.pichitube.app.core.network.ExtractorService 
          return (T) ExtractorModule_ProvideExtractorServiceFactory.provideExtractorService(singletonCImpl.providePichiTubeDownloaderProvider.get(), singletonCImpl.provideInnerTubeServiceProvider.get());

          case 4: // com.pichitube.app.core.network.InnerTubeService 
          return (T) ExtractorModule_ProvideInnerTubeServiceFactory.provideInnerTubeService(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideAppPreferencesRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.pichitube.app.core.data.datastore.AppPreferencesRepository 
          return (T) DataStoreModule_ProvideAppPreferencesRepositoryFactory.provideAppPreferencesRepository(singletonCImpl.provideDataStoreProvider.get());

          case 6: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.pichitube.app.core.data.db.PichiTubeDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.pichitube.app.feature.player.SharedPlaybackState 
          return (T) new SharedPlaybackState();

          case 9: // androidx.work.WorkManager 
          return (T) WorkerModule_ProvideWorkManagerFactory.provideWorkManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideWorkManagerConfigurationProvider.get());

          case 10: // androidx.work.Configuration 
          return (T) WorkerModule_ProvideWorkManagerConfigurationFactory.provideWorkManagerConfiguration(singletonCImpl.hiltWorkerFactory());

          case 11: // com.pichitube.app.core.network.DeArrowService 
          return (T) ExtractorModule_ProvideDeArrowServiceFactory.provideDeArrowService(singletonCImpl.provideOkHttpClientProvider.get());

          case 12: // com.pichitube.app.core.network.SponsorBlockService 
          return (T) ExtractorModule_ProvideSponsorBlockServiceFactory.provideSponsorBlockService(singletonCImpl.provideOkHttpClientProvider.get());

          case 13: // com.pichitube.app.core.network.ReturnYouTubeDislikeService 
          return (T) ExtractorModule_ProvideReturnYouTubeDislikeServiceFactory.provideReturnYouTubeDislikeService(singletonCImpl.provideOkHttpClientProvider.get());

          case 14: // com.pichitube.app.feature.player.PlaybackManager 
          return (T) new PlaybackManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideAppPreferencesRepositoryProvider.get(), singletonCImpl.sharedPlaybackStateProvider.get(), singletonCImpl.historyDao());

          case 15: // com.pichitube.app.feature.player.audio.AudioEqualizerManager 
          return (T) new AudioEqualizerManager();

          case 16: // com.pichitube.app.core.network.OtaUpdateService 
          return (T) ExtractorModule_ProvideOtaUpdateServiceFactory.provideOtaUpdateService(singletonCImpl.provideOkHttpClientProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
