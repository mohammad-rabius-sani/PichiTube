package com.pichitube.app.feature.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pichitube.app.core.data.db.entity.SubscriptionEntity
import com.pichitube.app.feature.subscriptions.SubscriptionsViewModel
import com.pichitube.app.ui.components.formatViews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(
    onChannelClick: (String) -> Unit,
    viewModel: SubscriptionsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Subscriptions", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        if (state.subscriptions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                    Icon(Icons.Default.Subscriptions, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "No subscriptions yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Subscribe to channels from the player screen",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 100.dp,
                )
            ) {
                items(state.subscriptions, key = { it.channelId }) { sub ->
                    SubscriptionRow(
                        subscription = sub,
                        onClick = { onChannelClick(sub.channelId) },
                        onUnsubscribe = { viewModel.unsubscribe(sub.channelId) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SubscriptionRow(
    subscription: SubscriptionEntity,
    onClick: () -> Unit,
    onUnsubscribe: () -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(subscription.channelName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        },
        supportingContent = {
            if (subscription.subscriberCount > 0) {
                Text("${subscription.subscriberCount.formatViews()} subscribers", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        leadingContent = {
            AsyncImage(
                model = subscription.channelAvatarUrl.ifBlank {
                    "https://ui-avatars.com/api/?name=${subscription.channelName.take(2)}&background=FF2D55&color=fff&size=44"
                },
                contentDescription = "Channel avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(44.dp).clip(CircleShape),
            )
        },
        trailingContent = {
            TextButton(onClick = onUnsubscribe) {
                Text("Unsubscribe", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        modifier = Modifier.clickable(onClick = onClick),
    )
}
