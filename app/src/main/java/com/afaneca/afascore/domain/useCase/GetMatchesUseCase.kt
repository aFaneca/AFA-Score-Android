package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.MatchListWrapper
import com.afaneca.afascore.domain.repository.FavoritesRepository
import com.afaneca.afascore.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase,
) {
    suspend operator fun invoke(): Flow<Resource<MatchListWrapper>> = flow {
        emit(Resource.Loading())
        val response = matchesRepository.getMatches()
        if (response is Resource.Success) {
            response.data?.let {
                val updatedData = mutableListOf<Match>()
                val favorites: List<String> = favoritesRepository.getFavorites() ?: emptyList()
                for (match in it) {
                    updatedData.add(match.copy(isFavorite = favorites.contains(match.id)))
                }
                val filterData = getReconciledFiltersUseCase(updatedData)
                val filteredMatches = getFilteredMatches(updatedData, filterData)
                emit(Resource.Success(MatchListWrapper(updatedData, filteredMatches, filterData)))
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