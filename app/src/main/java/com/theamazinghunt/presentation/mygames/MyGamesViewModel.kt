package com.theamazinghunt.presentation.mygames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class MyGamesUiState(
    val games: List<TreasureHuntGame> = emptyList()
)

class MyGamesViewModel(
    gameRepository: GameRepository
) : ViewModel() {
    val uiState: StateFlow<MyGamesUiState> = gameRepository.observeGames()
        .map { games -> MyGamesUiState(games = games) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyGamesUiState()
        )

    class Factory(
        private val gameRepository: GameRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyGamesViewModel::class.java)) {
                return MyGamesViewModel(gameRepository) as T
            }
            error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
