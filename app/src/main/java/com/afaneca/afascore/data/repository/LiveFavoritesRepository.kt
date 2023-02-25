package com.afaneca.afascore.data.repository

import com.afaneca.afascore.data.local.db.favorites.Favorite
import com.afaneca.afascore.data.local.db.favorites.FavoriteDao
import com.afaneca.afascore.domain.repository.FavoritesRepository
import javax.inject.Inject

class LiveFavoritesRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {
    override suspend fun getFavorites(): List<String> {
        return favoriteDao.getAll()
    }

    override suspend fun addFavorite(matchId: String): Boolean {
        favoriteDao.insert(Favorite(matchId))
        return true
    }

    override suspend fun removeFavorite(matchId: String): Boolean {
        favoriteDao.delete(Favorite(matchId))
        return true
    }
}