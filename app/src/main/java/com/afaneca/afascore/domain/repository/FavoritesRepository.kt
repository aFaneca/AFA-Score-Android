package com.afaneca.afascore.domain.repository

interface FavoritesRepository {
    suspend fun getFavorites(): List<String>
    suspend fun addFavorite(matchId: String): Boolean
    suspend fun removeFavorite(matchId: String): Boolean
}