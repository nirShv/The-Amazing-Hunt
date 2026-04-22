package com.theamazinghunt.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GameWithDetails(
    @Embedded val game: GameEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val points: List<PointOfInterestEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val clues: List<ClueTaskEntity>
)
