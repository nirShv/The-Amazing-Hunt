package com.theamazinghunt.data.mapper

import com.theamazinghunt.data.local.entity.ClueTaskEntity
import com.theamazinghunt.data.local.entity.GameEntity
import com.theamazinghunt.data.local.entity.GameWithDetails
import com.theamazinghunt.data.local.entity.PointOfInterestEntity
import com.theamazinghunt.domain.model.ClueTask
import com.theamazinghunt.domain.model.GeoPoint
import com.theamazinghunt.domain.model.PlayArea
import com.theamazinghunt.domain.model.PointOfInterest
import com.theamazinghunt.domain.model.TreasureHuntGame

fun GameWithDetails.asDomain(): TreasureHuntGame {
    val playArea = game.playAreaCenterLatitude?.let { latitude ->
        val longitude = game.playAreaCenterLongitude ?: return@let null
        val radiusMeters = game.playAreaRadiusMeters ?: return@let null
        PlayArea(
            center = GeoPoint(latitude = latitude, longitude = longitude),
            radiusMeters = radiusMeters
        )
    }

    return TreasureHuntGame(
        id = game.id,
        title = game.title,
        ageRange = game.ageRange,
        playersCount = game.playersCount,
        estimatedDurationMinutes = game.estimatedDurationMinutes,
        playArea = playArea,
        pointsOfInterest = points.sortedBy { it.orderIndex }.map { it.asDomain() },
        clues = clues.sortedBy { it.orderIndex }.map { it.asDomain() },
        status = game.status,
        createdAtMillis = game.createdAtMillis,
        updatedAtMillis = game.updatedAtMillis
    )
}

fun TreasureHuntGame.asEntity(): GameEntity = GameEntity(
    id = id,
    title = title,
    ageRange = ageRange,
    playersCount = playersCount,
    estimatedDurationMinutes = estimatedDurationMinutes,
    playAreaCenterLatitude = playArea?.center?.latitude,
    playAreaCenterLongitude = playArea?.center?.longitude,
    playAreaRadiusMeters = playArea?.radiusMeters,
    status = status,
    createdAtMillis = createdAtMillis,
    updatedAtMillis = updatedAtMillis
)

fun PointOfInterest.asEntity(gameId: String): PointOfInterestEntity = PointOfInterestEntity(
    id = id,
    gameId = gameId,
    title = title,
    description = description,
    latitude = location.latitude,
    longitude = location.longitude,
    orderIndex = orderIndex
)

fun ClueTask.asEntity(gameId: String): ClueTaskEntity = ClueTaskEntity(
    id = id,
    gameId = gameId,
    pointOfInterestId = pointOfInterestId,
    clueText = clueText,
    taskText = taskText,
    hintText = hintText,
    orderIndex = orderIndex
)

private fun PointOfInterestEntity.asDomain(): PointOfInterest = PointOfInterest(
    id = id,
    title = title,
    description = description,
    location = GeoPoint(latitude = latitude, longitude = longitude),
    orderIndex = orderIndex
)

private fun ClueTaskEntity.asDomain(): ClueTask = ClueTask(
    id = id,
    pointOfInterestId = pointOfInterestId,
    clueText = clueText,
    taskText = taskText,
    hintText = hintText,
    orderIndex = orderIndex
)
