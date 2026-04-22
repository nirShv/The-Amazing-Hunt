package com.theamazinghunt.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.theamazinghunt.data.local.entity.ClueTaskEntity
import com.theamazinghunt.data.local.entity.GameEntity
import com.theamazinghunt.data.local.entity.GameWithDetails
import com.theamazinghunt.data.local.entity.PointOfInterestEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GameDao {
    @Transaction
    @Query("SELECT * FROM games ORDER BY updatedAtMillis DESC")
    abstract fun observeGames(): Flow<List<GameWithDetails>>

    @Transaction
    @Query("SELECT * FROM games WHERE id = :gameId LIMIT 1")
    abstract fun observeGame(gameId: String): Flow<GameWithDetails?>

    @Upsert
    abstract suspend fun upsertGame(game: GameEntity)

    @Upsert
    abstract suspend fun upsertPoints(points: List<PointOfInterestEntity>)

    @Upsert
    abstract suspend fun upsertClues(clues: List<ClueTaskEntity>)

    @Query("DELETE FROM clue_tasks WHERE gameId = :gameId")
    abstract suspend fun deleteCluesForGame(gameId: String)

    @Query("DELETE FROM points_of_interest WHERE gameId = :gameId")
    abstract suspend fun deletePointsForGame(gameId: String)

    @Query("DELETE FROM games WHERE id = :gameId")
    abstract suspend fun deleteGame(gameId: String)

    @Transaction
    open suspend fun saveGame(
        game: GameEntity,
        points: List<PointOfInterestEntity>,
        clues: List<ClueTaskEntity>
    ) {
        upsertGame(game)
        deleteCluesForGame(game.id)
        deletePointsForGame(game.id)
        if (points.isNotEmpty()) upsertPoints(points)
        if (clues.isNotEmpty()) upsertClues(clues)
    }
}
