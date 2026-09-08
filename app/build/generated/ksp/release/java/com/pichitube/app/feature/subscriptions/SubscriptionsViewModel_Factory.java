package com.pichitube.app.feature.subscriptions;

import com.pichitube.app.core.data.db.dao.SubscriptionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class SubscriptionsViewModel_Factory implements Factory<SubscriptionsViewModel> {
  private final Provider<SubscriptionDao> subscriptionDaoProvider;

  public SubscriptionsViewModel_Factory(Provider<SubscriptionDao> subscriptionDaoProvider) {
    this.subscriptionDaoProvider = subscriptionDaoProvider;
  }

  @Override
  public SubscriptionsViewModel get() {
    return newInstance(subscriptionDaoProvider.get());
  }

  public static SubscriptionsViewModel_Factory create(
      javax.inject.Provider<SubscriptionDao> subscriptionDaoProvider) {
    return new SubscriptionsViewModel_Factory(Providers.asDaggerProvider(subscriptionDaoProvider));
  }

  public static SubscriptionsViewModel_Factory create(
      Provider<SubscriptionDao> subscriptionDaoProvider) {
    return new SubscriptionsViewModel_Factory(subscriptionDaoProvider);
  }

  public static SubscriptionsViewModel newInstance(SubscriptionDao subscriptionDao) {
    return new SubscriptionsViewModel(subscriptionDao);
  }
}
