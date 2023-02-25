package com.afaneca.afascore.data.local.db.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<String>

    @Insert
    suspend fun insert(vararg favorites: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)
}