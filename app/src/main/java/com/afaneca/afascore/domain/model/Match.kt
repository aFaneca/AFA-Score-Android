package com.afaneca.afascore.domain.model

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.ui.model.MatchUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Match(
    val id: String,
    val team1: Team,
    val team2: Team,
    val status: Constants.GameStatus,
    val scoreboard: Scoreboard?,
    val startDate: String?,
    val startTime: String?,
    val leagueDivision: String?,
    val hasRecentActivity: Boolean,
    val isFavorite: Boolean = false
) {
    companion object {
        fun mapFromUi(uiModel: MatchUiModel) = Match(
            id = uiModel.id,
            team1 = Team.mapFromUi(uiModel.team1),
            team2 = Team.mapFromUi(uiModel.team2),
            status = uiModel.status,
            scoreboard = uiModel.scoreboard?.let { Scoreboard.mapFromUi(it) },
            startDate = uiModel.startDate,
            startTime = uiModel.startTime,
            leagueDivision = uiModel.leagueDivision,
            hasRecentActivity = uiModel.hasRecentActivity,
            isFavorite = uiModel.isFavorite
        )
    }
}

fun Match.mapToUi() = MatchUiModel(
    this.id,
    this.team1.mapToUi(),
    this.team2.mapToUi(),
    this.status,
    this.scoreboard?.mapToUi(),
    this.startDate,
    this.startTime,
    this.leagueDivision,
    this.hasRecentActivity,
    this.isFavorite
)
