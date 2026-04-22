package com.theamazinghunt.presentation.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PlayGameUiState(
    val game: TreasureHuntGame? = null
)

class PlayGameViewModel(
    gameId: String,
    gameRepository: GameRepository
) : ViewModel() {
    val uiState: StateFlow<PlayGameUiState> = gameRepository.observeGame(gameId)
        .map { game -> PlayGameUiState(game = game) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PlayGameUiState()
        )

    class Factory(
        private val gameId: String,
        private val gameRepository: GameRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlayGameViewModel::class.java)) {
                return PlayGameViewModel(gameId, gameRepository) as T
            }
            error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
