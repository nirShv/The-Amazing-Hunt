package com.theamazinghunt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.navArgument
import com.theamazinghunt.AmazingHuntApplication
import com.theamazinghunt.di.AppContainer
import com.theamazinghunt.presentation.creategame.CreateGameBasicsScreen
import com.theamazinghunt.presentation.creategame.CreateGameCluesScreen
import com.theamazinghunt.presentation.creategame.CreateGamePlayAreaScreen
import com.theamazinghunt.presentation.creategame.CreateGamePointsScreen
import com.theamazinghunt.presentation.creategame.CreateGameReviewScreen
import com.theamazinghunt.presentation.creategame.CreateGameViewModel
import com.theamazinghunt.presentation.home.HomeScreen
import com.theamazinghunt.presentation.home.HomeViewModel
import com.theamazinghunt.presentation.mygames.MyGamesScreen
import com.theamazinghunt.presentation.mygames.MyGamesViewModel
import com.theamazinghunt.presentation.play.PlayGameScreen
import com.theamazinghunt.presentation.play.PlayGameViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val appContainer = (LocalContext.current.applicationContext as AmazingHuntApplication).appContainer

    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route,
        modifier = modifier
    ) {
        composable(AppRoute.Home.route) {
            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.Factory(appContainer.gameRepository)
            )
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState.value,
                onCreateGame = { navController.navigate(AppRoute.CreateGraph.route) },
                onOpenMyGames = { navController.navigate(AppRoute.MyGames.route) }
            )
        }

        composable(AppRoute.MyGames.route) {
            val viewModel: MyGamesViewModel = viewModel(
                factory = MyGamesViewModel.Factory(appContainer.gameRepository)
            )
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            MyGamesScreen(
                uiState = uiState.value,
                onBack = { navController.popBackStack() },
                onCreateGame = { navController.navigate(AppRoute.CreateGraph.route) },
                onOpenGame = { gameId ->
                    navController.navigate(AppRoute.PlayGame.createRoute(gameId))
                }
            )
        }

        composable(
            route = AppRoute.PlayGame.route,
            arguments = listOf(
                navArgument(AppRoute.PlayGame.GAME_ID_ARG) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments
                ?.getString(AppRoute.PlayGame.GAME_ID_ARG)
                .orEmpty()
            val viewModel: PlayGameViewModel = viewModel(
                factory = PlayGameViewModel.Factory(
                    gameId = gameId,
                    gameRepository = appContainer.gameRepository
                )
            )
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            PlayGameScreen(
                uiState = uiState.value,
                onBack = { navController.popBackStack() }
            )
        }

        navigation(
            startDestination = AppRoute.CreateBasics.route,
            route = AppRoute.CreateGraph.route
        ) {
            composable(AppRoute.CreateBasics.route) { backStackEntry ->
                val viewModel = createGameViewModel(
                    navController = navController,
                    backStackEntry = backStackEntry,
                    appContainer = appContainer
                )
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                CreateGameBasicsScreen(
                    uiState = uiState.value,
                    onTitleChange = viewModel::updateTitle,
                    onPlayersChange = viewModel::updatePlayersCount,
                    onDurationChange = viewModel::updateDurationMinutes,
                    onSaveDraft = viewModel::saveDraft,
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate(AppRoute.CreatePlayArea.route) }
                )
            }

            composable(AppRoute.CreatePlayArea.route) {
                CreateGamePlayAreaScreen(
                    onBack = { navController.popBackStack(AppRoute.Home.route, false) },
                    onPrevious = { navController.popBackStack() },
                    onNext = { navController.navigate(AppRoute.CreatePoints.route) }
                )
            }

            composable(AppRoute.CreatePoints.route) {
                CreateGamePointsScreen(
                    onBack = { navController.popBackStack(AppRoute.Home.route, false) },
                    onPrevious = { navController.popBackStack() },
                    onNext = { navController.navigate(AppRoute.CreateClues.route) }
                )
            }

            composable(AppRoute.CreateClues.route) {
                CreateGameCluesScreen(
                    onBack = { navController.popBackStack(AppRoute.Home.route, false) },
                    onPrevious = { navController.popBackStack() },
                    onNext = { navController.navigate(AppRoute.CreateReview.route) }
                )
            }

            composable(AppRoute.CreateReview.route) { backStackEntry ->
                val viewModel = createGameViewModel(
                    navController = navController,
                    backStackEntry = backStackEntry,
                    appContainer = appContainer
                )
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                CreateGameReviewScreen(
                    uiState = uiState.value,
                    onSaveDraft = viewModel::saveDraft,
                    onBack = { navController.popBackStack(AppRoute.Home.route, false) },
                    onPrevious = { navController.popBackStack() },
                    onFinish = { navController.popBackStack(AppRoute.Home.route, false) }
                )
            }
        }
    }
}

@Composable
private fun createGameViewModel(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    appContainer: AppContainer
): CreateGameViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(AppRoute.CreateGraph.route)
    }

    return viewModel(
        viewModelStoreOwner = parentEntry,
        factory = CreateGameViewModel.Factory(appContainer.gameRepository)
    )
}
