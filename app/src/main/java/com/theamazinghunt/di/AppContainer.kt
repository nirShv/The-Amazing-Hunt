package com.theamazinghunt.di

import android.content.Context
import androidx.room.Room
import com.theamazinghunt.data.local.AmazingHuntDatabase
import com.theamazinghunt.data.repository.MockAiClueGeneratorRepository
import com.theamazinghunt.data.repository.LocalGameRepository
import com.theamazinghunt.data.repository.MockGoogleMapsRepository
import com.theamazinghunt.data.repository.MockPdfPackageRepository
import com.theamazinghunt.data.repository.MockPlacesRepository
import com.theamazinghunt.domain.repository.AiClueGeneratorRepository
import com.theamazinghunt.domain.repository.GameRepository
import com.theamazinghunt.domain.repository.GoogleMapsRepository
import com.theamazinghunt.domain.repository.PdfPackageRepository
import com.theamazinghunt.domain.repository.PlacesRepository

class AppContainer(context: Context) {
    private val appContext = context.applicationContext

    private val database: AmazingHuntDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AmazingHuntDatabase::class.java,
            AmazingHuntDatabase.DATABASE_NAME
        ).build()
    }

    val gameRepository: GameRepository by lazy {
        LocalGameRepository(database.gameDao())
    }

    val googleMapsRepository: GoogleMapsRepository by lazy {
        MockGoogleMapsRepository()
    }

    val placesRepository: PlacesRepository by lazy {
        MockPlacesRepository()
    }

    val aiClueGeneratorRepository: AiClueGeneratorRepository by lazy {
        MockAiClueGeneratorRepository()
    }

    val pdfPackageRepository: PdfPackageRepository by lazy {
        MockPdfPackageRepository()
    }
}
