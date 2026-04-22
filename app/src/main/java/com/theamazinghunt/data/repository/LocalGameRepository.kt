package com.theamazinghunt.data.repository

import com.theamazinghunt.data.local.GameDao
import com.theamazinghunt.data.mapper.asDomain
import com.theamazinghunt.data.mapper.asEntity
import com.theamazinghunt.domain.model.TreasureHuntGame
import com.theamazinghunt.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGameRepository(
    private val gameDao: GameDao
) : GameRepository {
    override fun observeGames(): Flow<List<TreasureHuntGame>> {
        return gameDao.observeGames().map { games -> games.map { it.asDomain() } }
    }

    override fun observeGame(gameId: String): Flow<TreasureHuntGame?> {
        return gameDao.observeGame(gameId).map { it?.asDomain() }
    }

    override suspend fun saveGame(game: TreasureHuntGame) {
        gameDao.saveGame(
            game = game.asEntity(),
            points = game.pointsOfInterest.map { it.asEntity(game.id) },
            clues = game.clues.map { it.asEntity(game.id) }
        )
    }

    override suspend fun deleteGame(gameId: String) {
        gameDao.deleteGame(gameId)
    }
}
