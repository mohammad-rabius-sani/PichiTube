package com.pichitube.app.feature.player.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichitube.app.core.network.SponsorSegment
import com.pichitube.app.ui.components.formatDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayerControlsOverlay(
    isPlaying: Boolean,
    currentPositionMs: Long,
    durationMs: Long,
    bufferedPositionMs: Long,
    sponsorSegments: List<SponsorSegment>,
    isCcActive: Boolean,
    is2xSpeedActive: Boolean,
    isFullscreen: Boolean,
    videoTitle: String,
    onPlayPauseToggle: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onSeekRelative: (Long) -> Unit,
    onFastForwardHeld: (Boolean) -> Unit,
    onCcToggle: () -> Unit,
    onOptionsClick: () -> Unit,
    onAudioOnlyClick: () -> Unit = {},
    onFullscreenToggle: () -> Unit,
    onMinimize: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var areControlsVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Double tap visual indicator state
    var showRewindIndicator by remember { mutableStateOf(false) }
    var showForwardIndicator by remember { mutableStateOf(false) }

    // Auto-hide controls countdown
    LaunchedEffect(areControlsVisible, isPlaying) {
        if (areControlsVisible && isPlaying) {
            delay(3500)
            areControlsVisible = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        val job = coroutineScope.launch {
                            delay(450)
                            onFastForwardHeld(true)
                        }
                        tryAwaitRelease()
                        job.cancel()
                        onFastForwardHeld(false)
                    },
                    onTap = {
                        areControlsVisible = !areControlsVisible
                    },
                    onDoubleTap = { offset ->
                        val isLeft = offset.x < size.width / 2f
                        if (isLeft) {
                            onSeekRelative(-10000L)
                            showRewindIndicator = true
                            coroutineScope.launch {
                                delay(650)
                                showRewindIndicator = false
                            }
                        } else {
                            onSeekRelative(10000L)
                            showForwardIndicator = true
                            coroutineScope.launch {
                                delay(650)
                                showForwardIndicator = false
                            }
                        }
                    }
                )
            }
    ) {
        // 2X Speed HUD Badge
        AnimatedVisibility(
            visible = is2xSpeedActive,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Icon(
                    Icons.Default.FastForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "2X Speed",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        // Double-tap Seek Indicators (Left / Right) with YouTube-style pulsing ripple
        AnimatedVisibility(
            visible = showRewindIndicator,
            enter = fadeIn(animationSpec = tween(150)) + scaleIn(animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f)),
            exit = fadeOut(animationSpec = tween(250)),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterStart)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topEnd = 120.dp, bottomEnd = 120.dp))
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                YouTubeSeekRipple(isForward = false)
            }
        }

        AnimatedVisibility(
            visible = showForwardIndicator,
            enter = fadeIn(animationSpec = tween(150)) + scaleIn(animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f)),
            exit = fadeOut(animationSpec = tween(250)),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterEnd)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 120.dp, bottomStart = 120.dp))
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                YouTubeSeekRipple(isForward = true)
            }
        }

        // Full Controls Overlay (Fades in/out)
        AnimatedVisibility(
            visible = areControlsVisible,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.75f),
                                Color.Black.copy(alpha = 0.45f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            ) {
                // Top Bar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    IconButton(onClick = onMinimize) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Minimize",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = videoTitle,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    // CC Toggle Button
                    IconButton(onClick = onCcToggle) {
                        Icon(
                            if (isCcActive) Icons.Default.ClosedCaption else Icons.Outlined.ClosedCaption,
                            contentDescription = "Subtitles",
                            tint = if (isCcActive) MaterialTheme.colorScheme.primary else Color.White
                        )
                    }
                    // Audio Only / Background Listen Button
                    IconButton(onClick = onAudioOnlyClick) {
                        Icon(
                            Icons.Default.Headphones,
                            contentDescription = "Listen in Background",
                            tint = Color.White
                        )
                    }
                    // Player Settings / Options
                    IconButton(onClick = onOptionsClick) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Player options",
                            tint = Color.White
                        )
                    }
                }

                // Center Play/Pause & Seek Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ) {
                    // Rewind 10s
                    IconButton(
                        onClick = { onSeekRelative(-10000L) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Replay10,
                            contentDescription = "Rewind 10s",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.width(32.dp))

                    // Play / Pause main button with YouTube spring pop micro-animation
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        IconButton(
                            onClick = onPlayPauseToggle,
                            modifier = Modifier.size(64.dp)
                        ) {
                            AnimatedContent(
                                targetState = isPlaying,
                                transitionSpec = {
                                    (scaleIn(animationSpec = spring(dampingRatio = 0.65f, stiffness = 450f)) + fadeIn(tween(100))) togetherWith
                                    (scaleOut(animationSpec = tween(90)) + fadeOut(tween(90)))
                                },
                                label = "play_pause_pop"
                            ) { playing ->
                                Icon(
                                    if (playing) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (playing) "Pause" else "Play",
                                    tint = Color.White,
                                    modifier = Modifier.size(38.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.width(32.dp))

                    // Forward 10s
                    IconButton(
                        onClick = { onSeekRelative(10000L) },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Forward10,
                            contentDescription = "Forward 10s",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // Bottom Bar: Timeline Slider & Fullscreen Toggle
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    // Time and Fullscreen
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val currentText = (currentPositionMs / 1000L).formatDuration()
                        val durationText = (durationMs / 1000L).formatDuration()
                        Text(
                            text = "$currentText / $durationText",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(Modifier.weight(1f))

                        IconButton(
                            onClick = onFullscreenToggle,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                                contentDescription = if (isFullscreen) "Exit Fullscreen" else "Fullscreen",
                                tint = Color.White
                            )
                        }
                    }

                    // Scrubber with SponsorBlock highlight
                    val progress = if (durationMs > 0) (currentPositionMs.toFloat() / durationMs).coerceIn(0f, 1f) else 0f
                    val buffered = if (durationMs > 0) (bufferedPositionMs.toFloat() / durationMs).coerceIn(0f, 1f) else 0f

                    PlayerTimelineSlider(
                        progress = progress,
                        bufferedProgress = buffered,
                        durationMs = durationMs,
                        sponsorSegments = sponsorSegments,
                        onSeek = { newProgress ->
                            val targetMs = (newProgress * durationMs).toLong()
                            onSeekTo(targetMs)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerTimelineSlider(
    progress: Float,
    bufferedProgress: Float,
    durationMs: Long,
    sponsorSegments: List<SponsorSegment>,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var draggingProgress by remember { mutableStateOf<Float?>(null) }
    val displayProgress = draggingProgress ?: progress

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Canvas for track, buffered progress, and SponsorBlock segments
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
        ) {
            val width = size.width
            val height = size.height

            // Background inactive track
            drawRect(
                color = Color.White.copy(alpha = 0.25f),
                topLeft = Offset.Zero,
                size = Size(width, height)
            )

            // Buffered track
            drawRect(
                color = Color.White.copy(alpha = 0.45f),
                topLeft = Offset.Zero,
                size = Size(width * bufferedProgress, height)
            )

            // SponsorBlock highlight segments (amber/yellow)
            if (durationMs > 0) {
                sponsorSegments.forEach { seg ->
                    val segStart = (seg.startMs.toFloat() / durationMs).coerceIn(0f, 1f)
                    val segEnd = (seg.endMs.toFloat() / durationMs).coerceIn(0f, 1f)
                    val startX = segStart * width
                    val segWidth = (segEnd - segStart) * width
                    if (segWidth > 0) {
                        drawRect(
                            color = Color(0xFFFFB300), // Amber sponsor indicator
                            topLeft = Offset(startX, 0f),
                            size = Size(segWidth, height)
                        )
                    }
                }
            }

            // Active played track
            drawRect(
                color = Color(0xFFFF0000), // YouTube Red
                topLeft = Offset.Zero,
                size = Size(width * displayProgress, height)
            )
        }

        // Touch Slider
        Slider(
            value = displayProgress,
            onValueChange = { draggingProgress = it },
            onValueChangeFinished = {
                draggingProgress?.let { onSeek(it) }
                draggingProgress = null
            },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFFF0000),
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun YouTubeSeekRipple(isForward: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "SeekRipplePulse")
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.35f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(320, easing = LinearEasing), RepeatMode.Reverse),
        label = "SeekChevron1"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.55f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(320, delayMillis = 100, easing = LinearEasing), RepeatMode.Reverse),
        label = "SeekChevron2"
    )
    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.75f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(320, delayMillis = 200, easing = LinearEasing), RepeatMode.Reverse),
        label = "SeekChevron3"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isForward) {
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha1), modifier = Modifier.size(22.dp))
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha2), modifier = Modifier.size(22.dp))
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha3), modifier = Modifier.size(22.dp))
            } else {
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha3), modifier = Modifier.size(22.dp).graphicsLayer(rotationZ = 180f))
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha2), modifier = Modifier.size(22.dp).graphicsLayer(rotationZ = 180f))
                Icon(Icons.Default.PlayArrow, null, tint = Color.White.copy(alpha = alpha1), modifier = Modifier.size(22.dp).graphicsLayer(rotationZ = 180f))
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "10 seconds",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 0.5.sp
        )
    }
}

