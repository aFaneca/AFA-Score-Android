package com.afaneca.afascore.domain.model

import com.afaneca.afascore.data.remote.dto.TeamEntity

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Team(
    val fullName: String,
    val shortName: String?,
    val logoUrl: String?,
)


fun Team.mapToEntity() = TeamEntity(this.fullName, this.shortName, this.logoUrl)
