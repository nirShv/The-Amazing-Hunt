package com.theamazinghunt.domain.model

data class TreasureHuntGame(
    val id: String,
    val title: String,
    val ageRange: String,
    val playersCount: Int,
    val estimatedDurationMinutes: Int,
    val playArea: PlayArea?,
    val pointsOfInterest: List<PointOfInterest>,
    val clues: List<ClueTask>,
    val status: GameStatus,
    val createdAtMillis: Long,
    val updatedAtMillis: Long
)

enum class GameStatus {
    DRAFT,
    READY,
    COMPLETED
}
