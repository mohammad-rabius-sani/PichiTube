package com.pichitube.app.feature.home.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pichitube.app.core.network.VideoInfo
import com.pichitube.app.core.data.prefs.SUPPORTED_REGIONS
import com.pichitube.app.feature.home.HOME_CHIPS
import com.pichitube.app.feature.home.HomeViewModel
import com.pichitube.app.ui.components.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onVideoClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var notInterestedMenu by remember { mutableStateOf<VideoInfo?>(null) }
    var showRegionSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(
                    currentRegion = state.currentRegion,
                    scrollBehavior = scrollBehavior,
                    onRegionClick = { showRegionSheet = true },
                    onSearchClick = onSearchClick,
                    onSettingsClick = onSettingsClick,
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
        ) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding()),
            ) {
                when {
                    state.isLoading -> HomeLoadingSkeleton()
                    state.error != null && state.videos.isEmpty() -> HomeErrorState(
                        message = state.error!!,
                        onRetry = { viewModel.loadFeed() }
                    )
                    else -> HomeFeedList(
                        state = state,
                        onVideoClick = onVideoClick,
                        onChipSelect = viewModel::selectChip,
                        onLoadMore = viewModel::loadMore,
                        onNotInterested = { video -> notInterestedMenu = video },
                    )
                }
            }
        }

        // Region selector bottom sheet
        if (showRegionSheet) {
            RegionBottomSheet(
                currentRegion = state.currentRegion,
                onSelectRegion = viewModel::setRegion,
                onDismiss = { showRegionSheet = false },
            )
        }

        // Not Interested bottom sheet
        notInterestedMenu?.let { video ->
            VideoOptionsSheet(
                video = video,
                onDismiss = { notInterestedMenu = null },
                onNotInterested = {
                    viewModel.markNotInterested(video.videoId)
                    notInterestedMenu = null
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    currentRegion: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onRegionClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // PichiTube logo text
                Text(
                    text = "Pichi",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Tube",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        actions = {
            // Region Badge Pill
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable { onRegionClick() }
            ) {
                val region = com.pichitube.app.core.data.prefs.SUPPORTED_REGIONS.find {
                    it.code.equals(currentRegion, ignoreCase = true)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(region?.flag ?: "🇧🇩", fontSize = 13.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = currentRegion.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onBackground)
            }
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
private fun HomeFeedList(
    state: com.pichitube.app.feature.home.HomeUiState,
    onVideoClick: (String) -> Unit,
    onChipSelect: (String) -> Unit,
    onLoadMore: () -> Unit,
    onNotInterested: (VideoInfo) -> Unit,
) {
    val listState = rememberLazyListState()

    // Robust endless scroll trigger using snapshotFlow
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val total = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            total > 0 && lastVisible >= total - 4
        }
        .distinctUntilChanged()
        .filter { it }
        .collect {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        // Chips row
        item(key = "chips") {
            ChipRow(
                chips = HOME_CHIPS,
                selected = state.selectedChip,
                onSelect = onChipSelect,
            )
        }

        // Shorts row (if enabled)
        if (state.showShorts && state.shorts.isNotEmpty()) {
            item(key = "shorts_header") {
                SectionHeader("Shorts")
            }
            item(key = "shorts_row") {
                ShortsRow(
                    shorts = state.shorts,
                    onVideoClick = onVideoClick,
                )
            }
        }

        // Videos feed
        items(state.videos, key = { it.videoId }) { video ->
            VideoCard(
                video = video,
                onClick = { onVideoClick(video.videoId) },
                onLongClick = { onNotInterested(video) },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }

        // Loading more indicator
        if (state.isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun ChipRow(
    chips: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading YouTube Explore Compass Chip
        item(key = "explore_compass") {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (selected == "Trending") Color.White else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (selected == "Trending") Color(0xFF0F0F0F) else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSelect("Trending") }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "Explore",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        item(key = "divider") {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(1.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
            )
        }

        items(chips, key = { it }) { chip ->
            val isSelected = chip == selected
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (isSelected) Color(0xFF0F0F0F) else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSelect(chip) }
            ) {
                Text(
                    text = chip,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionBottomSheet(
    currentRegion: String,
    onSelectRegion: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Select Content Region",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(SUPPORTED_REGIONS) { region ->
                    val isSelected = region.code == currentRegion
                    ListItem(
                        headlineContent = {
                            Text(
                                text = "${region.flag}  ${region.name}",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            )
                        },
                        trailingContent = {
                            if (isSelected) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        },
                        modifier = Modifier
                            .clickable {
                                onSelectRegion(region.code)
                                onDismiss()
                            }
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShortsRow(shorts: List<VideoInfo>, onVideoClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(shorts, key = { it.videoId }) { short ->
            ShortsCard(video = short, onClick = { onVideoClick(short.videoId) })
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun HomeLoadingSkeleton() {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(6) {
                    Box(
                        modifier = Modifier
                            .width(72.dp)
                            .height(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ShimmerBrush())
                    )
                }
            }
        }
        items(5) {
            VideoCardSkeleton(modifier = Modifier.padding(horizontal = 12.dp))
        }
    }
}

@Composable
private fun HomeErrorState(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Icon(
                Icons.Default.WifiOff,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun VideoOptionsSheet(
    video: VideoInfo,
    onDismiss: () -> Unit,
    onNotInterested: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            ListItem(
                headlineContent = { Text("Not interested") },
                leadingContent = { Icon(Icons.Default.ThumbDownOffAlt, null) },
                modifier = Modifier.clickable(onClick = onNotInterested)
            )
            ListItem(
                headlineContent = { Text("Add to Watch Later") },
                leadingContent = { Icon(Icons.Default.WatchLater, null) },
                modifier = Modifier.clickable(onClick = onDismiss)
            )
            ListItem(
                headlineContent = { Text("Share") },
                leadingContent = { Icon(Icons.Default.Share, null) },
                modifier = Modifier.clickable(onClick = onDismiss)
            )
        }
    }
}
