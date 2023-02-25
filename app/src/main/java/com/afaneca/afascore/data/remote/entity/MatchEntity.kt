package com.afaneca.afascore.data.remote.entity

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.Scoreboard
import com.afaneca.afascore.domain.model.Team
import com.google.gson.annotations.SerializedName

/**
 * Created by António Faneca on 2/13/2023.
 */
data class MatchEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("team1")
    val team1: TeamEntity,
    @SerializedName("team2")
    val team2: TeamEntity,
    @SerializedName("status")
    val status: String,
    @SerializedName("scoreboard")
    val scoreboard: ScoreboardEntity?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("competition")
    val leagueDivision: String?,
    @SerializedName("hasRecentActivity")
    val hasRecentActivity: Boolean,
    @SerializedName("lastGameActivity")
    val lastGameActivity: String?
)

data class TeamEntity(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("logoUrl")
    val logoUrl: String?,
)

data class ScoreboardEntity(
    @SerializedName("team1Score")
    val team1Score: Int,
    @SerializedName("team2Score")
    val team2Score: Int,
)

fun ScoreboardEntity.mapToDomain() = Scoreboard(team1Score, team2Score)
fun TeamEntity.mapToDomain() = Team(fullName, shortName, logoUrl)
fun MatchEntity.mapToDomain() =
    Match(
        id,
        team1.mapToDomain(),
        team2.mapToDomain(),
        mapStatusToDomain(status),
        scoreboard?.mapToDomain(),
        startDate,
        startTime,
        leagueDivision,
        hasRecentActivity
    )

fun mapStatusToDomain(status: String): Constants.GameStatus {
    return when (status) {
        Constants.GameStatus.NotStarted.toString() -> Constants.GameStatus.NotStarted
        Constants.GameStatus.Ongoing.toString() -> Constants.GameStatus.Ongoing
        Constants.GameStatus.Finished.toString() -> Constants.GameStatus.Finished
        else -> Constants.GameStatus.Unknown
    }
}
