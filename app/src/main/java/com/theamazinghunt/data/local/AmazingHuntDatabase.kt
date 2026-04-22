package com.theamazinghunt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.theamazinghunt.data.local.entity.ClueTaskEntity
import com.theamazinghunt.data.local.entity.GameEntity
import com.theamazinghunt.data.local.entity.PointOfInterestEntity

@Database(
    entities = [
        GameEntity::class,
        PointOfInterestEntity::class,
        ClueTaskEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(RoomConverters::class)
abstract class AmazingHuntDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        const val DATABASE_NAME = "amazing_hunt.db"
    }
}
