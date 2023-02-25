package com.afaneca.afascore.ui.matchList

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.ui.model.MatchUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class MatchListState(
    val isLoading: Boolean = false,
    val isSneakyLoading: Boolean = false,
    val matchList: List<MatchUiModel>? = null,
    val filteredMatchList: List<MatchUiModel>? = null,
    val error: String? = null,
    val isFiltering: Boolean = false,
    val filterData: FilterDataUiModel? = null,
)

data class FilterDataUiModel(
    val teamsList: List<FilterableTeamUiModel>,
    val competitionsList: List<FilterableCompetitionUiModel>,
    val statusList: List<FilterableStatusUiModel>,
)

data class FilterableStatusUiModel(
    val status: Constants.GameStatus,
    val isSelected: Boolean = true
)
data class FilterableTeamUiModel(
    val name: String,
    val isSelected: Boolean = true
)

data class FilterableCompetitionUiModel(
    val name: String,
    val isSelected: Boolean = true
)