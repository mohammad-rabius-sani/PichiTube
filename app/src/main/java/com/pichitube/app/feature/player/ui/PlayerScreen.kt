package com.pichitube.app.feature.player.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.pichitube.app.feature.player.PlayerManager
import com.pichitube.app.ui.components.VideoCard
import com.pichitube.app.ui.components.formatViews

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    playerManager: PlayerManager,
    onMinimize: () -> Unit = { playerManager.minimize() },
    onVideoClick: (String) -> Unit = { playerManager.playVideo(it) },
    modifier: Modifier = Modifier,
) {
    val state by playerManager.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isFullscreen by remember { mutableStateOf(false) }
    var is2xSpeedHeld by remember { mutableStateOf(false) }
    var showOptionsSheet by remember { mutableStateOf(false) }

    // Keep screen awake while playing video, but release when paused or audio-only
    val activity = context as? Activity
    DisposableEffect(state.isPlaying, state.isAudioOnly) {
        if (state.isPlaying && !state.isAudioOnly) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    // Intercept hardware / system back button to collapse into Mini-Player
    BackHandler(enabled = true) {
        if (isFullscreen) {
            isFullscreen = false
        } else {
            onMinimize()
        }
    }

    // 2X speed press-and-hold boost
    LaunchedEffect(is2xSpeedHeld, state.playbackSpeed) {
        val targetSpeed = if (is2xSpeedHeld) 2.0f else state.playbackSpeed
        playerManager.exoPlayer.setPlaybackSpeed(targetSpeed)
    }

    // Options Bottom Sheet
    if (showOptionsSheet) {
        PlayerOptionsBottomSheet(
            selectedQuality = state.selectedQuality,
            availableQualities = state.availableQualities,
            onSelectQuality = playerManager::setQuality,
            selectedAudioLanguage = state.selectedAudioLanguage,
            availableAudioLanguages = state.availableAudioLanguages,
            onSelectAudioLanguage = playerManager::setAudioLanguage,
            selectedSubtitle = state.selectedSubtitle,
            availableSubtitles = state.availableSubtitles,
            onSelectSubtitle = playerManager::setSubtitle,
            playbackSpeed = state.playbackSpeed,
            onSelectSpeed = playerManager::setPlaybackSpeed,
            isLooping = state.isLooping,
            onToggleLoop = playerManager::toggleLoop,
            sleepTimerMinutes = state.sleepTimerMinutes,
            onSelectSleepTimer = playerManager::setSleepTimer,
            sponsorBlockEnabled = state.sponsorBlockEnabled,
            onToggleSponsorBlock = playerManager::toggleSponsorBlock,
            resolvedQuality = state.resolvedQuality,
            onDismiss = { showOptionsSheet = false },
        )
    }

    if (isFullscreen) {
        FullscreenPlayer(
            playerManager = playerManager,
            state = state,
            is2xSpeedActive = is2xSpeedHeld,
            onFastForwardHeld = { is2xSpeedHeld = it },
            onOptionsClick = { showOptionsSheet = true },
            onExitFullscreen = { isFullscreen = false },
            context = context,
        )
    } else {
        // YouTube-style: Flush video at the top, NO redundant sticky TopAppBar!
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Status bar spacer for edge-to-edge
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .background(Color.Black)
            )

            // 16:9 Video Player Surface
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = playerManager.exoPlayer
                            useController = false
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                            keepScreenOn = state.isPlaying && !state.isAudioOnly
                        }
                    },
                    update = { view ->
                        if (view.player != playerManager.exoPlayer) {
                            view.player = playerManager.exoPlayer
                        }
                        view.keepScreenOn = state.isPlaying && !state.isAudioOnly
                    },
                    modifier = Modifier.fillMaxSize(),
                )

                // Smooth video thumbnail preview with fade-out once video playback begins
                val activeThumbnail = state.previewThumbnailUrl ?: state.streamInfo?.thumbnailUrl
                androidx.compose.animation.AnimatedVisibility(
                    visible = (state.isLoading || !state.isPlaying) && !activeThumbnail.isNullOrBlank(),
                    enter = fadeIn(animationSpec = androidx.compose.animation.core.tween(200)),
                    exit = fadeOut(animationSpec = androidx.compose.animation.core.tween(400)),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = activeThumbnail,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                        )
                    }
                }

                // Custom Compose Player Overlay
                PlayerControlsOverlay(
                    isPlaying = state.isPlaying,
                    currentPositionMs = state.currentPositionMs,
                    durationMs = state.durationMs,
                    bufferedPositionMs = state.bufferedPositionMs,
                    sponsorSegments = state.sponsorSegments,
                    isCcActive = state.selectedSubtitle != null,
                    is2xSpeedActive = is2xSpeedHeld,
                    isFullscreen = false,
                    videoTitle = state.streamInfo?.title.orEmpty(),
                    onPlayPauseToggle = playerManager::togglePlayPause,
                    onSeekTo = playerManager::seekTo,
                    onSeekRelative = playerManager::seekRelative,
                    onFastForwardHeld = { is2xSpeedHeld = it },
                    onCcToggle = {
                        if (state.selectedSubtitle != null) {
                            playerManager.setSubtitle(null)
                        } else {
                            val firstSub = state.availableSubtitles.firstOrNull()
                            playerManager.setSubtitle(firstSub)
                        }
                    },
                    onOptionsClick = { showOptionsSheet = true },
                    onAudioOnlyClick = playerManager::playAsAudioOnly,
                    onFullscreenToggle = { isFullscreen = true },
                    onMinimize = onMinimize,
                )

                // Loading spinner
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            // Content Area with fluid slide/fade transition when switching videos
            AnimatedContent(
                targetState = state.videoId,
                transitionSpec = {
                    (fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) +
                     slideInVertically(animationSpec = androidx.compose.animation.core.spring(dampingRatio = 0.85f, stiffness = 400f)) { 40 })
                        .togetherWith(fadeOut(animationSpec = androidx.compose.animation.core.tween(180)))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                label = "VideoDetailsContentTransition"
            ) { _ ->
                if (state.error != null && state.streamInfo == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                            Icon(Icons.Default.ErrorOutline, null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.height(12.dp))
                            Text(state.error!!, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { playerManager.playVideo(state.videoId) }) { Text("Retry") }
                        }
                    }
                } else if (state.isLoading && state.streamInfo == null) {
                    // Shimmer skeleton loader while opening video
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        item {
                            val previewTitle = state.previewTitle
                            if (!previewTitle.isNullOrBlank()) {
                                Text(
                                    text = previewTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .height(14.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            )
                        }
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                )
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .width(130.dp)
                                            .height(14.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(10.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
                                    )
                                }
                            }
                        }
                        items(3) {
                            com.pichitube.app.ui.components.VideoCardSkeleton()
                        }
                    }
                } else {
                    state.streamInfo?.let { stream ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 96.dp),
                        ) {
                            // Title and Stats
                            item(key = "title") {
                                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                                    Text(
                                        text = stream.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = buildString {
                                            if (stream.viewCount > 0) append("${stream.viewCount.formatViews()} views")
                                            if (stream.uploadDate.isNotBlank()) append(" • ${stream.uploadDate}")
                                        },
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }

                            // Action Buttons Row (Like, Dislike, Save, Share, Options)
                            item(key = "actions") {
                                PlayerActionRow(
                                    isInWatchLater = state.isInWatchLater,
                                    likeCount = stream.likeCount,
                                    onWatchLater = playerManager::toggleWatchLater,
                                    onShare = {
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, "https://youtu.be/${state.videoId}")
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, "Share Video"))
                                    },
                                    onOptions = { showOptionsSheet = true },
                                )
                            }

                            // Channel Info Row
                            item(key = "channel") {
                                ChannelRow(
                                    channelName = stream.channelName,
                                    channelAvatarUrl = stream.channelAvatarUrl,
                                    subscriberCount = stream.subscriberCount,
                                    isSubscribed = state.isSubscribed,
                                )
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            // Expandable Description
                            if (stream.description.isNotBlank()) {
                                item(key = "description") {
                                    ExpandableDescription(
                                        description = stream.description,
                                        expanded = state.showDescription,
                                        onToggle = playerManager::toggleDescription,
                                    )
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }

                            // Related / Recommended Videos
                            if (stream.relatedVideos.isNotEmpty()) {
                                item(key = "related_header") {
                                    Text(
                                        "Related Videos",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }
                                items(stream.relatedVideos, key = { "rel_${it.videoId}" }) { video ->
                                    VideoCard(
                                        video = video,
                                        onClick = { onVideoClick(video.videoId) },
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerActionRow(
    isInWatchLater: Boolean,
    likeCount: Long,
    onWatchLater: () -> Unit,
    onShare: () -> Unit,
    onOptions: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ActionButton(
            icon = Icons.Default.ThumbUp,
            label = if (likeCount > 0) likeCount.formatViews() else "Like",
            onClick = {},
        )
        ActionButton(
            icon = Icons.Default.ThumbDown,
            label = "Dislike",
            onClick = {},
        )
        ActionButton(
            icon = Icons.Default.WatchLater,
            label = if (isInWatchLater) "Saved" else "Save",
            onClick = onWatchLater,
            tint = if (isInWatchLater) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
        ActionButton(
            icon = Icons.Default.Share,
            label = "Share",
            onClick = onShare,
        )
        ActionButton(
            icon = Icons.Default.Tune,
            label = "Options",
            onClick = onOptions,
        )
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(3.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ChannelRow(
    channelName: String,
    channelAvatarUrl: String,
    subscriberCount: Long,
    isSubscribed: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = channelAvatarUrl.ifBlank {
                "https://ui-avatars.com/api/?name=${channelName.take(2)}&background=FF2D55&color=fff&size=40"
            },
            contentDescription = "Channel avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
        )
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = channelName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (subscriberCount > 0) {
                Text(
                    text = "${subscriberCount.formatViews()} subscribers",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Button(
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSubscribed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primary,
                contentColor = if (isSubscribed) MaterialTheme.colorScheme.onSurface else Color.White,
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        ) {
            Text(if (isSubscribed) "Subscribed" else "Subscribe", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun ExpandableDescription(
    description: String,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
        )
        TextButton(onClick = onToggle) {
            Text(
                text = if (expanded) "Show less" else "Show more",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun FullscreenPlayer(
    playerManager: PlayerManager,
    state: com.pichitube.app.feature.player.PlayerState,
    is2xSpeedActive: Boolean,
    onFastForwardHeld: (Boolean) -> Unit,
    onOptionsClick: () -> Unit,
    onExitFullscreen: () -> Unit,
    context: android.content.Context,
) {
    val activity = context as? Activity
    val view = LocalView.current

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        val controller = WindowCompat.getInsetsController(activity!!.window, view)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = playerManager.exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    keepScreenOn = state.isPlaying
                }
            },
            update = { view ->
                view.keepScreenOn = state.isPlaying
            },
            modifier = Modifier.fillMaxSize(),
        )

        PlayerControlsOverlay(
            isPlaying = state.isPlaying,
            currentPositionMs = state.currentPositionMs,
            durationMs = state.durationMs,
            bufferedPositionMs = state.bufferedPositionMs,
            sponsorSegments = state.sponsorSegments,
            isCcActive = state.selectedSubtitle != null,
            is2xSpeedActive = is2xSpeedActive,
            isFullscreen = true,
            videoTitle = state.streamInfo?.title.orEmpty(),
            onPlayPauseToggle = playerManager::togglePlayPause,
            onSeekTo = playerManager::seekTo,
            onSeekRelative = playerManager::seekRelative,
            onFastForwardHeld = onFastForwardHeld,
            onCcToggle = {
                if (state.selectedSubtitle != null) {
                    playerManager.setSubtitle(null)
                } else {
                    val firstSub = state.availableSubtitles.firstOrNull()
                    playerManager.setSubtitle(firstSub)
                }
            },
            onOptionsClick = onOptionsClick,
            onAudioOnlyClick = playerManager::playAsAudioOnly,
            onFullscreenToggle = onExitFullscreen,
            onMinimize = onExitFullscreen,
        )
    }
}
