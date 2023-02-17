package com.afaneca.afascore.domain.model

import com.afaneca.afascore.data.remote.entity.ScoreboardEntity
import com.afaneca.afascore.ui.model.ScoreboardUiModel
import com.afaneca.afascore.ui.model.TeamUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Scoreboard(
    val team1Score: Int,
    val team2Score: Int
) {
    companion object {
        fun mapFromUi(uiModel: ScoreboardUiModel) =
            Scoreboard(uiModel.team1Score, uiModel.team2Score)
    }
}

fun Scoreboard.mapToEntity() = ScoreboardEntity(team1Score, team2Score)
fun Scoreboard.mapToUi() = ScoreboardUiModel(team1Score, team2Score)

