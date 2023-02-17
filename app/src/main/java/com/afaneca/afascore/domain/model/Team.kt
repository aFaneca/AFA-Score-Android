package com.afaneca.afascore.domain.model

import com.afaneca.afascore.data.remote.entity.TeamEntity
import com.afaneca.afascore.ui.model.TeamUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Team(
    val fullName: String,
    val shortName: String?,
    val logoUrl: String?,
) {
    companion object {
        fun mapFromUi(uiModel: TeamUiModel) =
            Team(uiModel.fullName, uiModel.shortName, uiModel.logoUrl)
    }
}


fun Team.mapToUi() = TeamUiModel(this.fullName, this.shortName, this.logoUrl)
