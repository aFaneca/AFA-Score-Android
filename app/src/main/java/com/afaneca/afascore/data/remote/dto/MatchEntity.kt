package com.afaneca.afascore.data.remote.dto

import com.afaneca.afascore.domain.model.Match
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
    @SerializedName("result")
    val result: String?,
)

data class TeamEntity(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("logoUrl")
    val logoUrl: String?,
)

fun TeamEntity.mapToDomain() = Team(fullName, shortName, logoUrl)
fun MatchEntity.mapToDomain() = Match(id, team1.mapToDomain(), team2.mapToDomain(), status, result)