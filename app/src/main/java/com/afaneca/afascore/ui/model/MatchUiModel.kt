package com.afaneca.afascore.ui.model

import com.google.gson.annotations.SerializedName

/**
 * Created by António Faneca on 2/13/2023.
 */
data class MatchUiModel(
    val id: String,
    val team1: TeamUiModel,
    val team2: TeamUiModel,
    val status: String,
    val scoreboard: ScoreboardUiModel?,
    val startDate: String?,
    val startTime: String?,
    val leagueDivision: String?,
)