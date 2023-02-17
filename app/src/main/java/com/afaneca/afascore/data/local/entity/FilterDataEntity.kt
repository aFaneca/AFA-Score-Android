package com.afaneca.afascore.data.local.entity

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.FilterableCompetition
import com.afaneca.afascore.domain.model.FilterableStatus
import com.afaneca.afascore.domain.model.FilterableTeam


/**
 * Created by António Faneca on 2/16/2023.
 */
data class FilterDataEntity(
    val teamsList: List<FilterableTeamEntity>,
    val competitionsList: List<FilterableCompetitionEntity>,
    val statusList: List<FilterableStatusEntity>
) {
    companion object {
        fun mapFromDomain(domainModel: FilterData) =
            FilterDataEntity(teamsList = domainModel.teamsList.map {
                FilterableTeamEntity.mapFromDomain(
                    it
                )
            }, competitionsList = domainModel.competitionsList.map {
                FilterableCompetitionEntity.mapFromDomain(
                    it
                )
            }, statusList = domainModel.statusList.map { FilterableStatusEntity.mapFromDomain(it) })
    }
}

data class FilterableStatusEntity(
    val status: Constants.GameStatus, val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableStatus) =
            FilterableStatusEntity(domainModel.status, domainModel.isSelected)
    }
}

data class FilterableTeamEntity(
    val name: String, val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableTeam) =
            FilterableTeamEntity(domainModel.name, domainModel.isSelected)
    }
}

data class FilterableCompetitionEntity(
    val name: String, val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableCompetition) =
            FilterableCompetitionEntity(domainModel.name, domainModel.isSelected)
    }
}

fun FilterableTeamEntity.mapToDomain() = FilterableTeam(this.name, this.isSelected)
fun FilterableCompetitionEntity.mapToDomain() = FilterableCompetition(this.name, this.isSelected)
fun FilterableStatusEntity.mapToDomain() = FilterableStatus(this.status, this.isSelected)
fun FilterDataEntity.mapToDomain() = FilterData(teamsList.map { it.mapToDomain() },
    competitionsList.map { it.mapToDomain() },
    statusList.map { it.mapToDomain() })


