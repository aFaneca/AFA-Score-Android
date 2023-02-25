package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    suspend operator fun invoke(): Flow<List<String>> = flow {
        val list = favoritesRepository.getFavorites() ?: emptyList()
        emit(list)
    }
}