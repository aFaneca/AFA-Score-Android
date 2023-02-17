package com.afaneca.afascore.domain.model

import androidx.annotation.StringRes
import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.data.local.entity.FilterDataEntity
import com.afaneca.afascore.data.local.entity.FilterableCompetitionEntity
import com.afaneca.afascore.data.local.entity.FilterableStatusEntity
import com.afaneca.afascore.data.local.entity.FilterableTeamEntity
import com.afaneca.afascore.ui.matchList.FilterDataUiModel
import com.afaneca.afascore.ui.matchList.FilterableCompetitionUiModel
import com.afaneca.afascore.ui.matchList.FilterableStatusUiModel
import com.afaneca.afascore.ui.matchList.FilterableTeamUiModel
import com.afaneca.afascore.ui.model.TeamUiModel

/**
 * Created by António Faneca on 2/16/2023.
 */
data class FilterData(
    val teamsList: List<FilterableTeam>,
    val competitionsList: List<FilterableCompetition>,
    val statusList: List<FilterableStatus>
) {
    companion object {
        fun mapFromDomain(uiModel: FilterDataUiModel): FilterData =
            FilterData(teamsList = uiModel.teamsList.map {
                FilterableTeam.mapFromDomain(
                    it
                )
            }, competitionsList = uiModel.competitionsList.map {
                FilterableCompetition.mapFromDomain(
                    it
                )
            }, statusList = uiModel.statusList.map { FilterableStatus.mapFromDomain(it) })
    }
}

data class FilterableStatus(
    val status: Constants.GameStatus,
    val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(uiModel: FilterableStatusUiModel) =
            FilterableStatus(uiModel.status, uiModel.isSelected)
    }
}

data class FilterableTeam(
    val name: String,
    val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(uiModel: FilterableTeamUiModel) =
            FilterableTeam(uiModel.name, uiModel.isSelected)
    }
}

data class FilterableCompetition(
    val name: String,
    val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(uiModel: FilterableCompetitionUiModel) =
            FilterableCompetition(uiModel.name, uiModel.isSelected)
    }
}

fun FilterableTeam.mapToUi() = FilterableTeamUiModel(this.name, this.isSelected)
fun FilterableCompetition.mapToUi() = FilterableCompetitionUiModel(this.name, this.isSelected)
fun FilterableStatus.mapToUi() = FilterableStatusUiModel(this.status, this.isSelected)
fun FilterData.mapToUi() =
    FilterDataUiModel(
        teamsList.map { it.mapToUi() },
        competitionsList.map { it.mapToUi() },
        statusList.map { it.mapToUi() })