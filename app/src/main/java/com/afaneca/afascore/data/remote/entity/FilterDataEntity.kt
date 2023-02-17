package com.afaneca.afascore.data.remote.entity

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.model.FilterableCompetition
import com.afaneca.afascore.domain.model.FilterableStatus
import com.afaneca.afascore.domain.model.FilterableTeam

/**
 * Created by António Faneca on 2/17/2023.
 */
data class FilterDataEntity(
    val teamsList: List<FilterableTeamEntity>,
    val competitionsList: List<FilterableCompetitionEntity>,
    val statusList: List<FilterableStatusEntity>
) {
    companion object {
        fun mapFromDomain(domainModel: FilterData) =
            FilterDataEntity(teamsList = domainModel.teamsList.map {
                FilterableTeamEntity.mapFromDomain(it)
            }, competitionsList = domainModel.competitionsList.map {
                FilterableCompetitionEntity.mapFromDomain(it)
            }, statusList = domainModel.statusList.map {
                FilterableStatusEntity.mapFromDomain(it)
            })
    }
}

data class FilterableStatusEntity(
    val status: String, // sealed classes are not serializable, so we store it as string
    val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableStatus) = FilterableStatusEntity(
            status = domainModel.status.toString(), isSelected = domainModel.isSelected
        )
    }
}

data class FilterableTeamEntity(
    val name: String, val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableTeam) =
            FilterableTeamEntity(name = domainModel.name, isSelected = domainModel.isSelected)
    }
}

data class FilterableCompetitionEntity(
    val name: String, val isSelected: Boolean = true
) {
    companion object {
        fun mapFromDomain(domainModel: FilterableCompetition) = FilterableCompetitionEntity(
            name = domainModel.name, isSelected = domainModel.isSelected
        )
    }
}

fun FilterableStatusEntity.mapToDomain() = FilterableStatus(
    status = when (status) {
        Constants.GameStatus.NotStarted.toString() -> Constants.GameStatus.NotStarted
        Constants.GameStatus.Ongoing.toString() -> Constants.GameStatus.Ongoing
        Constants.GameStatus.Finished.toString() -> Constants.GameStatus.Finished
        else -> Constants.GameStatus.Unknown
    }, isSelected = isSelected
)

fun FilterableTeamEntity.mapToDomain() = FilterableTeam(name, isSelected)
fun FilterableCompetitionEntity.mapToDomain() = FilterableCompetition(name, isSelected)
fun FilterDataEntity.mapToDomain() = FilterData(teamsList = teamsList.map { it.mapToDomain() },
    competitionsList = competitionsList.map { it.mapToDomain() },
    statusList = statusList.map { it.mapToDomain() })