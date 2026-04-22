package com.theamazinghunt.data.repository

import com.theamazinghunt.domain.model.PlayArea
import com.theamazinghunt.domain.repository.GoogleMapsRepository
import com.theamazinghunt.domain.repository.PlayAreaValidation

class MockGoogleMapsRepository : GoogleMapsRepository {
    override suspend fun validatePlayArea(playArea: PlayArea): Result<PlayAreaValidation> {
        return Result.success(
            PlayAreaValidation(
                isPlayable = playArea.radiusMeters in MIN_RADIUS_METERS..MAX_RADIUS_METERS
            )
        )
    }

    private companion object {
        const val MIN_RADIUS_METERS = 20
        const val MAX_RADIUS_METERS = 3_000
    }
}
