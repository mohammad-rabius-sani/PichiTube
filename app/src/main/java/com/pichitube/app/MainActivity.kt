package com.pichitube.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pichitube.app.core.data.prefs.AppPreferences
import com.pichitube.app.core.data.prefs.PrefsRepository
import com.pichitube.app.core.update.UpdateManager
import com.pichitube.app.feature.player.PlayerDisplayMode
import com.pichitube.app.feature.player.PlayerManager
import com.pichitube.app.feature.player.ui.PlayerScreen
import com.pichitube.app.navigation.NavGraph
import com.pichitube.app.navigation.Routes
import com.pichitube.app.ui.components.MiniPlayerBar
import com.pichitube.app.ui.components.MiniPlayerState
import com.pichitube.app.ui.components.UpdateModal
import com.pichitube.app.ui.theme.PichiTubeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var prefsRepository: PrefsRepository
    @Inject lateinit var playerManager: PlayerManager
    @Inject lateinit var updateManager: UpdateManager

    private val pipModeState = mutableStateOf(false)
    var onUserLeaveHintCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Full edge-to-edge: draw behind status bar AND nav bar
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)

        // Allow content to draw into display cutout area (notch)
        window.attributes.layoutInDisplayCutoutMode =
            android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS

        addOnPictureInPictureModeChangedListener { info ->
            pipModeState.value = info.isInPictureInPictureMode
        }

        // Prompt for notification permission on Android 13+ for media playback controls
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        // Silent background check for updates
        updateManager.checkForUpdates(isManualCheck = false)

        setContent {
            val prefs by prefsRepository.preferences.collectAsStateWithLifecycle(
                initialValue = AppPreferences()
            )

            PichiTubeTheme(themeMode = prefs.themeMode) {
                PichiTubeApp(
                    playerManager = playerManager,
                    updateManager = updateManager,
                    isPipMode = pipModeState.value,
                )
            }
        }
    }

    fun enterPip() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                val params = android.app.PictureInPictureParams.Builder()
                    .setAspectRatio(android.util.Rational(16, 9))
                    .build()
                enterPictureInPictureMode(params)
            } catch (e: Exception) { /* not supported */ }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // Strictly only enter PiP if a video is actively playing!
        val state = playerManager.state.value
        if (state.isPlaying && state.videoId.isNotEmpty() && state.displayMode != PlayerDisplayMode.HIDDEN) {
            enterPip()
        }
        onUserLeaveHintCallback?.invoke()
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun PichiTubeApp(
    playerManager: PlayerManager,
    updateManager: UpdateManager,
    isPipMode: Boolean,
) {
    val playerState by playerManager.state.collectAsStateWithLifecycle()
    val updateState by updateManager.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val bottomNavRoutes = setOf(Routes.HOME, Routes.SHORTS, Routes.SUBSCRIPTIONS, Routes.LIBRARY)
    val showBottomBar = currentDestination?.hierarchy?.any { it.route in bottomNavRoutes } ?: true

    // Pause main player if navigating to Shorts tab to prevent dual audio
    LaunchedEffect(currentDestination?.route) {
        if (currentDestination?.route == Routes.SHORTS) {
            if (playerState.isPlaying) {
                playerManager.exoPlayer.pause()
            }
        }
    }

    if (isPipMode) {
        // Picture-in-Picture dedicated clean video surface
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = playerManager.exoPlayer
                        useController = false
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    // Smoothly slide bottom bar off-screen when full player expands, and slide in when collapsed
                    AnimatedVisibility(
                        visible = playerState.displayMode != PlayerDisplayMode.EXPANDED,
                        enter = slideInVertically(
                            animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f),
                            initialOffsetY = { it }
                        ) + fadeIn(tween(150)),
                        exit = slideOutVertically(
                            animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f),
                            targetOffsetY = { it }
                        ) + fadeOut(tween(150)),
                    ) {
                        Surface(
                            color = Color(0xFF0F0F0F),
                            tonalElevation = 4.dp,
                            shadowElevation = 8.dp,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = if (!showBottomBar) Modifier.navigationBarsPadding() else Modifier
                            ) {
                                // Mini-player docked above bottom navigation (hidden on Shorts tab)
                                val isShortsRoute = currentDestination?.route == Routes.SHORTS
                                if (playerState.displayMode == PlayerDisplayMode.COLLAPSED && playerState.videoId.isNotEmpty() && !isShortsRoute) {
                                    val progress = if (playerState.durationMs > 0) {
                                        (playerState.currentPositionMs.toFloat() / playerState.durationMs).coerceIn(0f, 1f)
                                    } else 0f

                                    MiniPlayerBar(
                                        state = MiniPlayerState(
                                            videoId = playerState.videoId,
                                            title = playerState.streamInfo?.title.orEmpty(),
                                            channelName = playerState.streamInfo?.channelName.orEmpty(),
                                            thumbnailUrl = playerState.streamInfo?.thumbnailUrl.orEmpty(),
                                            isPlaying = playerState.isPlaying,
                                            isVisible = true,
                                        ),
                                        progress = progress,
                                        onExpand = playerManager::expand,
                                        onPlayPause = playerManager::togglePlayPause,
                                        onDismiss = playerManager::close,
                                    )
                                }

                                // Bottom navigation bar
                                if (showBottomBar) {
                                    NavigationBar(
                                        tonalElevation = 0.dp,
                                        containerColor = Color(0xFF0F0F0F),
                                        windowInsets = NavigationBarDefaults.windowInsets,
                                    ) {
                                        BottomNavItem.all.forEach { item ->
                                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                                            val iconScale by animateFloatAsState(
                                                targetValue = if (selected) 1.15f else 1.0f,
                                                animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
                                                label = "NavIconScale_${item.route}"
                                            )
                                            NavigationBarItem(
                                                selected = selected,
                                                onClick = {
                                                    navController.navigate(item.route) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = if (selected) item.filledIcon else item.outlinedIcon,
                                                        contentDescription = item.label,
                                                        modifier = Modifier.scale(iconScale),
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = item.label,
                                                        fontSize = 10.sp,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                    )
                                                },
                                                colors = NavigationBarItemDefaults.colors(
                                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                                ),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
            ) { innerPadding ->
                NavGraph(
                    navController = navController,
                    onVideoClick = { videoId ->
                        playerManager.playVideo(videoId)
                    },
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }

            // Full Player Screen Overlay with YouTube spring physics
            AnimatedVisibility(
                visible = playerState.displayMode == PlayerDisplayMode.EXPANDED,
                enter = slideInVertically(
                    animationSpec = spring(dampingRatio = 0.82f, stiffness = 380f),
                    initialOffsetY = { it }
                ) + fadeIn(animationSpec = tween(220)),
                exit = slideOutVertically(
                    animationSpec = spring(dampingRatio = 0.88f, stiffness = 420f),
                    targetOffsetY = { it }
                ) + fadeOut(animationSpec = tween(180)),
            ) {
                PlayerScreen(
                    playerManager = playerManager,
                    onMinimize = playerManager::minimize,
                    onVideoClick = { playerManager.playVideo(it) },
                )
            }

            // Global In-App Direct Self-Updater Modal
            UpdateModal(
                state = updateState,
                onDownloadClick = updateManager::startDownload,
                onInstallClick = updateManager::installApk,
                onDismiss = updateManager::dismiss,
            )
        }
    }
}

private sealed class BottomNavItem(
    val route: String,
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
) {
    data object Home : BottomNavItem(
        Routes.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home
    )
    data object Shorts : BottomNavItem(
        Routes.SHORTS, "Shorts", Icons.Filled.PlayArrow, Icons.Outlined.PlayArrow
    )
    data object Subscriptions : BottomNavItem(
        Routes.SUBSCRIPTIONS, "Subscriptions", Icons.Filled.Subscriptions, Icons.Outlined.Subscriptions
    )
    data object Library : BottomNavItem(
        Routes.LIBRARY, "You", Icons.Filled.VideoLibrary, Icons.Outlined.VideoLibrary
    )

    companion object {
        val all = listOf(Home, Shorts, Subscriptions, Library)
    }
}
