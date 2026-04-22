package com.theamazinghunt.data.repository

import com.theamazinghunt.domain.model.PdfPackage
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.PdfPackageRepository

class MockPdfPackageRepository : PdfPackageRepository {
    override suspend fun createPackage(game: TreasureHuntGame): Result<PdfPackage> {
        return Result.success(
            PdfPackage(
                gameId = game.id,
                fileName = "${game.id}.pdf",
                uriString = ""
            )
        )
    }
}
