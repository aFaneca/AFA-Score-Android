package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(matchId: String): Boolean {
        return favoritesRepository.addFavorite(matchId)
    }
}