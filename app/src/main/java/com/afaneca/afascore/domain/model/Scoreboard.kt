package com.afaneca.afascore.domain.model

import com.afaneca.afascore.data.remote.entity.ScoreboardEntity
import com.afaneca.afascore.ui.model.ScoreboardUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
data class Scoreboard(
    val team1Score: Int,
    val team2Score: Int
)

fun Scoreboard.mapToEntity() = ScoreboardEntity(team1Score, team2Score)
fun Scoreboard.mapToUi() = ScoreboardUiModel(team1Score, team2Score)

