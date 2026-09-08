package com.pichitube.app.feature.shorts.ui

import android.content.Intent
import androidx.annotation.OptIn
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.pichitube.app.core.network.VideoInfo
import com.pichitube.app.feature.shorts.ShortsViewModel
import com.pichitube.app.ui.components.formatViews
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun ShortsScreen(
    viewModel: ShortsViewModel = hiltViewModel(),
    onVideoClick: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (uiState.isLoading && uiState.shorts.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color(0xFFFF0033))
                Spacer(Modifier.height(16.dp))
                Text("Loading Shorts...", color = Color.White)
            }
        }
        return
    }

    if (uiState.error != null && uiState.shorts.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = Color(0xFFFF0033),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = uiState.error.orEmpty(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.loadShorts() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0033))
                ) {
                    Text("Retry", color = Color.White)
                }
            }
        }
        return
    }

    val shorts = uiState.shorts
    if (shorts.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("No Shorts available right now", color = Color.White)
        }
        return
    }

    val pagerState = rememberPagerState(pageCount = { shorts.size })

    // Single dedicated ExoPlayer instance for smooth looping playback across pages
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    // When page changes, load and play the active short
    var isShortPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(false) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isShortPlaying = playing
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                isBuffering = (playbackState == Player.STATE_BUFFERING)
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    LaunchedEffect(pagerState.currentPage, shorts) {
        if (pagerState.currentPage in shorts.indices) {
            val currentShort = shorts[pagerState.currentPage]
            exoPlayer.stop()
            isBuffering = true

            // Trigger load more when near end
            if (pagerState.currentPage >= shorts.size - 3) {
                viewModel.loadMore()
            }

            // Prefetch next short
            if (pagerState.currentPage + 1 in shorts.indices) {
                coroutineScope.launch {
                    viewModel.getShortStream(shorts[pagerState.currentPage + 1].videoId)
                }
            }

            val streamUrl = viewModel.getShortStream(currentShort.videoId)
            if (streamUrl != null) {
                val mediaItem = MediaItem.fromUri(streamUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            } else {
                isBuffering = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            key = { index -> if (index in shorts.indices) shorts[index].videoId else index }
        ) { page ->
            val short = shorts[page]
            val isCurrentPage = (pagerState.currentPage == page)

            ShortPageItem(
                short = short,
                isCurrentPage = isCurrentPage,
                exoPlayer = exoPlayer,
                isPlaying = isShortPlaying,
                isBuffering = isBuffering,
                onTogglePlayPause = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                },
                onShare = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "https://youtube.com/shorts/${short.videoId}")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, "Share Short"))
                }
            )
        }

        // Top App Bar / Title overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Shorts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun ShortPageItem(
    short: VideoInfo,
    isCurrentPage: Boolean,
    exoPlayer: ExoPlayer,
    isPlaying: Boolean,
    isBuffering: Boolean,
    onTogglePlayPause: () -> Unit,
    onShare: () -> Unit,
) {
    var showPlayPauseIndicator by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onTogglePlayPause()
                    showPlayPauseIndicator = true
                }
            )
    ) {
        // Thumbnail backdrop
        AsyncImage(
            model = short.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // If this page is currently active, render the video player
        if (isCurrentPage) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        keepScreenOn = true
                    }
                },
                update = { view ->
                    if (view.player != exoPlayer) {
                        view.player = exoPlayer
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Scrim gradient for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.85f),
                        )
                    )
                )
        )

        // Buffering spinner
        if (isCurrentPage && isBuffering) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFF0033), modifier = Modifier.size(48.dp))
            }
        }

        // Animated Play/Pause indicator in center on tap
        LaunchedEffect(showPlayPauseIndicator) {
            if (showPlayPauseIndicator) {
                delay(600)
                showPlayPauseIndicator = false
            }
        }

        AnimatedVisibility(
            visible = showPlayPauseIndicator,
            enter = fadeIn() + scaleIn(initialScale = 0.8f),
            exit = fadeOut() + scaleOut(targetScale = 1.2f),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.PlayArrow else Icons.Default.Pause,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        // Bottom and Right Content Overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Channel & Title info (Left column)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                // Channel Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (short.channelAvatarUrl.isNotBlank()) {
                        AsyncImage(
                            model = short.channelAvatarUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(
                        text = short.channelName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Title / Caption
                Text(
                    text = short.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }

            // Action Buttons (Right vertical column)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var isLiked by remember { mutableStateOf(false) }

                // Like
                ShortActionButton(
                    icon = if (isLiked) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp,
                    label = if (short.likeCount > 0) short.likeCount.formatViews() else if (isLiked) "1" else "Like",
                    tint = if (isLiked) Color(0xFFFF0033) else Color.White,
                    onClick = { isLiked = !isLiked }
                )

                // Dislike
                ShortActionButton(
                    icon = Icons.Outlined.ThumbDown,
                    label = "Dislike",
                    tint = Color.White,
                    onClick = { }
                )

                // Share
                ShortActionButton(
                    icon = Icons.Outlined.Share,
                    label = "Share",
                    tint = Color.White,
                    onClick = onShare
                )
            }
        }
    }
}

@Composable
private fun ShortActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    tint: Color,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        if (label.isNotBlank()) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
