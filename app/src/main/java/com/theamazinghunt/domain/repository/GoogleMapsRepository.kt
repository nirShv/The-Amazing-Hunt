package com.theamazinghunt.domain.repository

import com.theamazinghunt.domain.model.PlayArea

interface GoogleMapsRepository {
    suspend fun validatePlayArea(playArea: PlayArea): Result<PlayAreaValidation>
}

data class PlayAreaValidation(
    val isPlayable: Boolean,
    val warnings: List<String> = emptyList()
)
