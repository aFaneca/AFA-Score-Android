package com.afaneca.afascore.domain.model

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.data.remote.entity.MatchEntity
import com.afaneca.afascore.ui.model.MatchUiModel
import com.google.gson.annotations.SerializedName

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
)

fun Match.mapToEntity() = MatchEntity(
    this.id,
    this.team1.mapToEntity(),
    this.team2.mapToEntity(),
    this.status.toString(),
    this.scoreboard?.mapToEntity(),
    this.startDate,
    this.startTime,
    this.leagueDivision
)

fun Match.mapToUi() =
    MatchUiModel(
        this.id,
        this.team1.mapToUi(),
        this.team2.mapToUi(),
        this.status,
        this.scoreboard?.mapToUi(),
        this.startDate,
        this.startTime,
        this.leagueDivision
    )
