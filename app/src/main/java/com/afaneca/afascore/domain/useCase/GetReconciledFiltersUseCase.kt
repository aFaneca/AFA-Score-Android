package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.FilterableCompetition
import com.afaneca.afascore.domain.model.FilterableStatus
import com.afaneca.afascore.domain.model.FilterableTeam
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.repository.FilterRepository
import java.util.HashSet
import javax.inject.Inject

/**
 * Fetches filter data from storage and reconciles it with the most up-to-date match list
 * Created by António Faneca on 2/17/2023.
 */
class GetReconciledFiltersUseCase @Inject constructor(
    private val filtersRepository: FilterRepository
) {
    /**
     * Loops through the match [list] to find unique team and competition items.
     * Reconciles them with locally stored filters and bundles the resulting data
     * * in a [FilterData] object
     */
    suspend operator fun invoke(list: List<Match>): FilterData {
        val savedFilters: FilterData? = getSavedFilters()
        val teamSet = HashSet<String>()
        val competitionSet = HashSet<String>()

        // Get distinct values for teams and competitions
        for (match in list) {
            teamSet.add(match.team1.fullName)
            teamSet.add(match.team2.fullName)
            match.leagueDivision?.let { competitionSet.add(it) }
        }

        /**
         * If saved filters exist: default every filter not in the saved filters list to isSelected=false
         * Else: default every filter to isSelected=true
         */
        return FilterData(
            teamsList = teamSet.map {
                if (savedFilters == null || savedFilters.teamsList.isEmpty()) {
                    FilterableTeam(it, true)
                } else {
                    FilterableTeam(
                        it,
                        savedFilters.teamsList.firstOrNull { t -> t.name == it }?.isSelected
                            ?: false
                    )
                }
            },
            competitionsList = competitionSet.map {
                if (savedFilters == null || savedFilters.competitionsList.isEmpty()) {
                    FilterableCompetition(it, true)
                } else {
                    FilterableCompetition(
                        it,
                        savedFilters.competitionsList.firstOrNull { c -> c.name == it }?.isSelected
                            ?: false
                    )
                }
            },
            statusList = run {
                val statusList = listOf(
                    FilterableStatus(Constants.GameStatus.NotStarted, true),
                    FilterableStatus(Constants.GameStatus.Ongoing, true),
                    FilterableStatus(Constants.GameStatus.Finished, true),
                )
                if (savedFilters == null || savedFilters.statusList.isEmpty()) {
                    statusList
                } else {
                    statusList.map {
                        FilterableStatus(
                            it.status,
                            savedFilters.statusList.firstOrNull { status -> status.status == it.status }?.isSelected
                                ?: false
                        )
                    }
                }
            }
        )
    }

    private suspend fun getSavedFilters(): FilterData? {
        return filtersRepository.getFilterData()
    }
}