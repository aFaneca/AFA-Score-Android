package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.FilterableCompetition
import com.afaneca.afascore.domain.model.FilterableStatus
import com.afaneca.afascore.domain.model.FilterableTeam
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.MatchListWrapper
import com.afaneca.afascore.domain.model.Scoreboard
import com.afaneca.afascore.domain.model.Team
import com.afaneca.afascore.domain.repository.FilterRepository
import com.afaneca.afascore.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.HashSet

import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val filtersRepository: FilterRepository,
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase
) {
    suspend operator fun invoke(): Flow<Resource<MatchListWrapper>> = flow {
        emit(Resource.Loading())
        val response = matchesRepository.getMatches()
        if (response is Resource.Success) {
            response.data?.let {
                val filterData = getReconciledFiltersUseCase(it)
                emit(Resource.Success(MatchListWrapper(response.data, filterData)))
            }

        } else emit(Resource.Error("", null))
    }
}