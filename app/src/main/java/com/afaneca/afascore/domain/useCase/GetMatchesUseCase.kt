package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
){
    operator fun invoke() : Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())
        emit(repository.getMatches())
    }
}