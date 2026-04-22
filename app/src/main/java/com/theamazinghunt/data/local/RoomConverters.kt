package com.theamazinghunt.data.local

import androidx.room.TypeConverter
import com.theamazinghunt.domain.model.GameStatus

class RoomConverters {
    @TypeConverter
    fun gameStatusToString(status: GameStatus): String = status.name

    @TypeConverter
    fun stringToGameStatus(value: String): GameStatus = GameStatus.valueOf(value)
}
