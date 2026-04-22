package com.theamazinghunt.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "points_of_interest",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId")]
)
data class PointOfInterestEntity(
    @PrimaryKey val id: String,
    val gameId: String,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val orderIndex: Int
)
