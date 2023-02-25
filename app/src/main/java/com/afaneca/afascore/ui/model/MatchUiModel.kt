package com.afaneca.afascore.ui.model

import com.afaneca.afascore.common.Constants

/**
 * Created by António Faneca on 2/13/2023.
 */
data class MatchUiModel(
    val id: String,
    val team1: TeamUiModel,
    val team2: TeamUiModel,
    val status: Constants.GameStatus,
    val scoreboard: ScoreboardUiModel?,
    val startDate: String?,
    val startTime: String?,
    val leagueDivision: String?,
    val hasRecentActivity: Boolean,
    val isFavorite: Boolean = false
)