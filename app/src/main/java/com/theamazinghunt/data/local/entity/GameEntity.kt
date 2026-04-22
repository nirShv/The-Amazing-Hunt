package com.theamazinghunt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theamazinghunt.domain.model.GameStatus

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String,
    val title: String,
    val ageRange: String,
    val playersCount: Int,
    val estimatedDurationMinutes: Int,
    val playAreaCenterLatitude: Double?,
    val playAreaCenterLongitude: Double?,
    val playAreaRadiusMeters: Int?,
    val status: GameStatus,
    val createdAtMillis: Long,
    val updatedAtMillis: Long
)
