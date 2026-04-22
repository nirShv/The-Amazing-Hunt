package com.theamazinghunt.domain.repository

import com.theamazinghunt.domain.model.TreasureHuntGame
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun observeGames(): Flow<List<TreasureHuntGame>>
    fun observeGame(gameId: String): Flow<TreasureHuntGame?>
    suspend fun saveGame(game: TreasureHuntGame)
    suspend fun deleteGame(gameId: String)
}
