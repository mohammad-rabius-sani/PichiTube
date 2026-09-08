package com.pichitube.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pichitube.app.feature.home.ui.HomeScreen
import com.pichitube.app.feature.library.ui.LibraryScreen
import com.pichitube.app.feature.search.ui.SearchScreen
import com.pichitube.app.feature.settings.ui.SettingsScreen
import com.pichitube.app.feature.subscriptions.ui.SubscriptionsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onVideoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(220)) },
        exitTransition = { fadeOut(animationSpec = tween(180)) },
        popEnterTransition = { fadeIn(animationSpec = tween(220)) },
        popExitTransition = { fadeOut(animationSpec = tween(180)) },
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onVideoClick = onVideoClick,
                onSearchClick = { navController.navigate(Routes.SEARCH) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
            )
        }

        composable(
            route = Routes.SEARCH,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f)) + fadeIn(animationSpec = tween(200)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeOut(animationSpec = tween(180)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f)) + fadeOut(animationSpec = tween(180)) },
        ) {
            SearchScreen(
                onVideoClick = onVideoClick,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.SHORTS) {
            com.pichitube.app.feature.shorts.ui.ShortsScreen(
                onVideoClick = onVideoClick,
            )
        }

        composable(Routes.SUBSCRIPTIONS) {
            SubscriptionsScreen(
                onChannelClick = { /* Channel screen */ },
            )
        }

        composable(Routes.LIBRARY) {
            LibraryScreen(
                onVideoClick = onVideoClick,
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
            )
        }

        composable(
            route = Routes.SETTINGS,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f)) + fadeIn(animationSpec = tween(200)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeOut(animationSpec = tween(180)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 3 }, animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = spring(dampingRatio = 0.85f, stiffness = 400f)) + fadeOut(animationSpec = tween(180)) },
        ) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.PLAYER,
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: return@composable
            onVideoClick(videoId)
            navController.popBackStack()
        }
    }
}
