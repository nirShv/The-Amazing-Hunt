package com.theamazinghunt.domain.repository

import com.theamazinghunt.domain.model.PdfPackage
import com.theamazinghunt.domain.model.TreasureHuntGame

interface PdfPackageRepository {
    suspend fun createPackage(game: TreasureHuntGame): Result<PdfPackage>
}
