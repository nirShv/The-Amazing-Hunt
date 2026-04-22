package com.theamazinghunt.domain.repository

import com.theamazinghunt.domain.model.PlayArea
import com.theamazinghunt.domain.model.PointOfInterest

interface PlacesRepository {
    suspend fun searchPlaces(query: String, playArea: PlayArea): Result<List<PointOfInterest>>
}
