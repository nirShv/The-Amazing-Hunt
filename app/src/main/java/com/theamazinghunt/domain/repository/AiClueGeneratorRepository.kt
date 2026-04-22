package com.theamazinghunt.domain.repository

import com.theamazinghunt.domain.model.ClueTask
import com.theamazinghunt.domain.model.PointOfInterest
import com.theamazinghunt.domain.model.TreasureHuntGame

interface AiClueGeneratorRepository {
    suspend fun generateClues(request: ClueGenerationRequest): Result<List<ClueTask>>
}

data class ClueGenerationRequest(
    val game: TreasureHuntGame,
    val pointsOfInterest: List<PointOfInterest>,
    val languageTag: String
)
