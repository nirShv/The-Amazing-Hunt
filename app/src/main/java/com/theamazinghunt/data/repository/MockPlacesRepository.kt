package com.theamazinghunt.data.repository

import com.theamazinghunt.domain.model.PlayArea
import com.theamazinghunt.domain.model.PointOfInterest
import com.theamazinghunt.domain.repository.PlacesRepository

class MockPlacesRepository : PlacesRepository {
    override suspend fun searchPlaces(
        query: String,
        playArea: PlayArea
    ): Result<List<PointOfInterest>> {
        return Result.success(emptyList())
    }
}
