package com.afaneca.afascore.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afaneca.afascore.data.local.db.favorites.Favorite
import com.afaneca.afascore.data.local.db.favorites.FavoriteDao

@Database(entities = [Favorite::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}