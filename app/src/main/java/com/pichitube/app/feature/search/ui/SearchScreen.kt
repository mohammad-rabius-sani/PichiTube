package com.pichitube.app.feature.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pichitube.app.core.network.SearchResultItem
import com.pichitube.app.feature.search.SearchViewModel
import com.pichitube.app.ui.components.ChannelCard
import com.pichitube.app.ui.components.PlaylistCard
import com.pichitube.app.ui.components.VideoCard
import com.pichitube.app.ui.components.VideoCardSkeleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onVideoClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                title = {
                    TextField(
                        value = state.query,
                        onValueChange = viewModel::onQueryChange,
                        placeholder = { Text("Search PichiTube…", fontSize = 16.sp) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            keyboard?.hide()
                            viewModel.search()
                        }),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        ),
                        trailingIcon = {
                            if (state.query.isNotBlank()) {
                                IconButton(onClick = { viewModel.onQueryChange("") }) {
                                    Icon(Icons.Default.Clear, "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    )
                },
                actions = {
                    // Search filters button with active indicator badge
                    IconButton(onClick = viewModel::openFilterSheet) {
                        BadgedBox(
                            badge = {
                                if (!state.filter.isDefault) {
                                    Badge(containerColor = Color(0xFFFF0033))
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Tune,
                                contentDescription = "Search filters",
                                tint = if (!state.filter.isDefault) Color(0xFFFF0033) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    IconButton(onClick = {
                        keyboard?.hide()
                        viewModel.search()
                    }) {
                        Icon(Icons.Default.Search, "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()).fillMaxSize()) {
            when {
                // 1. Actively loading search results
                state.isSearching -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 100.dp, top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(5) {
                            VideoCardSkeleton(modifier = Modifier.padding(horizontal = 12.dp))
                        }
                    }
                }

                // 2. Real-time YouTube search suggestions (while typing and no submitted results yet or suggestions active)
                state.query.isNotBlank() && state.suggestions.isNotEmpty() && state.results.isEmpty() -> {
                    LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                        items(state.suggestions) { suggestion ->
                            val annotated = buildAnnotatedString {
                                val lowerSug = suggestion.lowercase()
                                val lowerQ = state.query.lowercase()
                                val idx = lowerSug.indexOf(lowerQ)
                                if (idx >= 0) {
                                    append(suggestion.substring(0, idx))
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(suggestion.substring(idx, idx + state.query.length))
                                    }
                                    append(suggestion.substring(idx + state.query.length))
                                } else {
                                    append(suggestion)
                                }
                            }

                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = annotated,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                trailingContent = {
                                    IconButton(onClick = {
                                        viewModel.onQueryChange(suggestion)
                                    }) {
                                        Icon(
                                            Icons.Default.NorthWest,
                                            contentDescription = "Insert into search",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        keyboard?.hide()
                                        viewModel.search(suggestion)
                                    }
                            )
                        }
                    }
                }

                // 3. Search results displayed
                state.results.isNotEmpty() -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 100.dp, top = 4.dp),
                    ) {
                        items(
                            items = state.results,
                            key = { item ->
                                when (item) {
                                    is SearchResultItem.VideoItem -> "v_${item.video.videoId}"
                                    is SearchResultItem.ChannelItem -> "c_${item.channelId}"
                                    is SearchResultItem.PlaylistItem -> "p_${item.playlistId}"
                                }
                            }
                        ) { item ->
                            when (item) {
                                is SearchResultItem.VideoItem -> {
                                    VideoCard(
                                        video = item.video,
                                        onClick = { onVideoClick(item.video.videoId) },
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    )
                                }
                                is SearchResultItem.ChannelItem -> {
                                    ChannelCard(
                                        item = item,
                                        onClick = {
                                            // Search for channel videos when clicked
                                            viewModel.onQueryChange(item.title)
                                            viewModel.search(item.title)
                                        },
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                                    )
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                    )
                                }
                                is SearchResultItem.PlaylistItem -> {
                                    PlaylistCard(
                                        item = item,
                                        onClick = {
                                            // Search for playlist when clicked
                                            viewModel.onQueryChange(item.title)
                                            viewModel.search(item.title)
                                        },
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // 4. No results matching active filters
                state.rawResults.isNotEmpty() && state.results.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                            Icon(
                                Icons.Default.FilterAltOff,
                                null,
                                modifier = Modifier.size(56.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "No results match your active filters.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = viewModel::resetFilter) {
                                Text("Reset filters")
                            }
                        }
                    }
                }

                // 5. Error state
                state.error != null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.SearchOff,
                                null,
                                modifier = Modifier.size(56.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                state.error!!,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(onClick = { viewModel.search() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                // 6. Recent searches list (when query is blank)
                state.query.isBlank() && state.recentSearches.isNotEmpty() -> {
                    LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Recent searches",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                TextButton(onClick = viewModel::clearHistory) {
                                    Text("Clear all")
                                }
                            }
                        }
                        items(state.recentSearches) { recentQuery ->
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = recentQuery,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.History,
                                        null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                trailingContent = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = { viewModel.removeRecentSearch(recentQuery) }) {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Remove search",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        IconButton(onClick = { viewModel.onQueryChange(recentQuery) }) {
                                            Icon(
                                                Icons.Default.NorthWest,
                                                contentDescription = "Insert into search",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        keyboard?.hide()
                                        viewModel.search(recentQuery)
                                    }
                            )
                        }
                    }
                }

                // 7. Default idle empty screen
                else -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Search,
                                null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Search for videos, music, channels…",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // Search Filter Bottom Sheet Modal
        if (state.isFilterSheetOpen) {
            SearchFilterBottomSheet(
                currentFilter = state.filter,
                onApply = viewModel::applyFilterSelection,
                onReset = viewModel::resetFilter,
                onDismiss = viewModel::closeFilterSheet,
            )
        }
    }
}
