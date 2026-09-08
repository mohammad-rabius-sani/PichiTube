package com.pichitube.app.feature.library.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pichitube.app.feature.library.LibraryViewModel
import com.pichitube.app.ui.components.formatDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onVideoClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("History", "Watch Later")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("You", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()).fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(tab, style = MaterialTheme.typography.labelLarge) }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    // History tab
                    if (state.history.isEmpty()) {
                        EmptyState(Icons.Default.History, "No watch history yet")
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = viewModel::clearHistory) {
                                Icon(Icons.Default.DeleteSweep, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Clear all")
                            }
                        }
                        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                            items(state.history, key = { it.videoId }) { item ->
                                ListItem(
                                    headlineContent = {
                                        Text(item.title, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleSmall)
                                    },
                                    supportingContent = {
                                        Text("${item.channelName} • ${item.durationSeconds.formatDuration()}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    },
                                    leadingContent = {
                                        AsyncImage(
                                            model = item.thumbnailUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(width = 80.dp, height = 56.dp).clip(RoundedCornerShape(6.dp)),
                                        )
                                    },
                                    modifier = Modifier.clickable { onVideoClick(item.videoId) }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // Watch Later tab
                    if (state.watchLater.isEmpty()) {
                        EmptyState(Icons.Default.WatchLater, "No videos saved for later")
                    } else {
                        LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                            items(state.watchLater, key = { it.videoId }) { item ->
                                ListItem(
                                    headlineContent = {
                                        Text(item.title, maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleSmall)
                                    },
                                    supportingContent = {
                                        Text("${item.channelName} • ${item.durationSeconds.formatDuration()}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    },
                                    leadingContent = {
                                        AsyncImage(
                                            model = item.thumbnailUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(width = 80.dp, height = 56.dp).clip(RoundedCornerShape(6.dp)),
                                        )
                                    },
                                    trailingContent = {
                                        IconButton(onClick = { viewModel.removeFromWatchLater(item.videoId) }) {
                                            Icon(Icons.Default.Delete, "Remove", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    },
                                    modifier = Modifier.clickable { onVideoClick(item.videoId) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    message: String,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Icon(icon, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Text(message, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
