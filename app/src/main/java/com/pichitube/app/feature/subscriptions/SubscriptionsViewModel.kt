package com.pichitube.app.feature.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichitube.app.core.data.db.dao.SubscriptionDao
import com.pichitube.app.core.data.db.entity.SubscriptionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubscriptionsUiState(val subscriptions: List<SubscriptionEntity> = emptyList())

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val subscriptionDao: SubscriptionDao,
) : ViewModel() {

    val uiState: StateFlow<SubscriptionsUiState> = subscriptionDao.getAll()
        .map { SubscriptionsUiState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SubscriptionsUiState())

    fun unsubscribe(channelId: String) = viewModelScope.launch {
        subscriptionDao.deleteById(channelId)
    }
}
