package com.theamazinghunt.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clue_tasks",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId"), Index("pointOfInterestId")]
)
data class ClueTaskEntity(
    @PrimaryKey val id: String,
    val gameId: String,
    val pointOfInterestId: String,
    val clueText: String,
    val taskText: String,
    val hintText: String?,
    val orderIndex: Int
)
