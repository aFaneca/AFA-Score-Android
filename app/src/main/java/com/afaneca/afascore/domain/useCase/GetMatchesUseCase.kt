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
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase
) {
    suspend operator fun invoke(): Flow<Resource<MatchListWrapper>> = flow {
        emit(Resource.Loading())
        val response = matchesRepository.getMatches()
        if (response is Resource.Success) {
            response.data?.let {
                val filterData = getReconciledFiltersUseCase(it)
                val filteredMatches = getFilteredMatches(it, filterData)
                emit(Resource.Success(MatchListWrapper(it, filteredMatches, filterData)))
            }

        } else emit(Resource.Error("", null))
    }

    private fun getFilteredMatches(matches: List<Match>, filterData: FilterData): List<Match> {
        val filteredList = mutableListOf<Match>()
        for (match in matches) {
            val selectedCompetitions = filterData.competitionsList.filter { it.isSelected }
            val selectedTeams = filterData.teamsList.filter { it.isSelected }
            val selectedStatuses = filterData.statusList.filter { it.isSelected }

            if (selectedCompetitions.firstOrNull { it.name == match.leagueDivision } != null
                && selectedTeams.firstOrNull { it.name == match.team1.fullName || it.name == match.team2.fullName } != null
                && selectedStatuses.firstOrNull { it.status == match.status } != null) {
                filteredList.add(match)
            }
        }

        return filteredList
    }
}