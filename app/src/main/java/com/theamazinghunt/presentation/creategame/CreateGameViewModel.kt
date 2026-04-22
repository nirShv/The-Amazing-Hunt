package com.theamazinghunt.presentation.creategame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.theamazinghunt.domain.model.GameStatus
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.GameRepository
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateGameUiState(
    val title: String = "",
    val playersCount: String = DEFAULT_PLAYERS_COUNT,
    val durationMinutes: String = DEFAULT_DURATION_MINUTES,
    val isSaving: Boolean = false,
    val draftSaved: Boolean = false
)

class CreateGameViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateGameUiState())
    val uiState: StateFlow<CreateGameUiState> = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, draftSaved = false) }
    }

    fun updatePlayersCount(playersCount: String) {
        _uiState.update {
            it.copy(
                playersCount = playersCount.onlyDigits(maxLength = 2),
                draftSaved = false
            )
        }
    }

    fun updateDurationMinutes(durationMinutes: String) {
        _uiState.update {
            it.copy(
                durationMinutes = durationMinutes.onlyDigits(maxLength = 3),
                draftSaved = false
            )
        }
    }

    fun saveDraft(defaultTitle: String) {
        val current = _uiState.value
        if (current.isSaving) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, draftSaved = false) }
            val now = System.currentTimeMillis()
            val game = TreasureHuntGame(
                id = UUID.randomUUID().toString(),
                title = current.title.trim().ifEmpty { defaultTitle },
                ageRange = DEFAULT_AGE_RANGE,
                playersCount = current.playersCount.toIntOrNull()
                    ?.coerceAtLeast(1)
                    ?: DEFAULT_PLAYERS_COUNT.toInt(),
                estimatedDurationMinutes = current.durationMinutes.toIntOrNull()
                    ?.coerceAtLeast(5)
                    ?: DEFAULT_DURATION_MINUTES.toInt(),
                playArea = null,
                pointsOfInterest = emptyList(),
                clues = emptyList(),
                status = GameStatus.DRAFT,
                createdAtMillis = now,
                updatedAtMillis = now
            )
            gameRepository.saveGame(game)
            _uiState.update { it.copy(isSaving = false, draftSaved = true) }
        }
    }

    class Factory(
        private val gameRepository: GameRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateGameViewModel::class.java)) {
                return CreateGameViewModel(gameRepository) as T
            }
            error("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

private fun String.onlyDigits(maxLength: Int): String {
    return filter { it.isDigit() }.take(maxLength)
}

private const val DEFAULT_PLAYERS_COUNT = "4"
private const val DEFAULT_DURATION_MINUTES = "45"
private const val DEFAULT_AGE_RANGE = "6-10"
