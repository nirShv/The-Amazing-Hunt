package com.theamazinghunt.presentation.navigation

sealed class AppRoute(val route: String) {
    data object Home : AppRoute("home")
    data object MyGames : AppRoute("my-games")
    data object PlayGame : AppRoute("play-game/{gameId}") {
        const val GAME_ID_ARG = "gameId"

        fun createRoute(gameId: String): String = "play-game/$gameId"
    }
    data object CreateGraph : AppRoute("create-game")
    data object CreateBasics : AppRoute("create-game/basics")
    data object CreatePlayArea : AppRoute("create-game/play-area")
    data object CreatePoints : AppRoute("create-game/points")
    data object CreateClues : AppRoute("create-game/clues")
    data object CreateReview : AppRoute("create-game/review")
}
