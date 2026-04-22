package com.theamazinghunt.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val recentGames: List<TreasureHuntGame> = emptyList()
)

class HomeViewModel(
    gameRepository: GameRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = gameRepository.observeGames()
        .map { games -> HomeUiState(recentGames = games.take(3)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    class Factory(
        private val gameRepository: GameRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(gameRepository) as T
            }
            error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
