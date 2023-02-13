package com.afaneca.afascore.domain.model

import com.afaneca.afascore.data.remote.dto.MatchEntity

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Match(
    val id: String,
    val team1: Team,
    val team2: Team,
    val status: String,
    val result: String?,
)

fun Match.mapToEntity() = MatchEntity(this.id, this.team1.mapToEntity(), this.team2.mapToEntity(), this.status, this.result)
