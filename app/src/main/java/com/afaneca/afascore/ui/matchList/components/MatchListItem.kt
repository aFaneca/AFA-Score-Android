package com.afaneca.afascore.ui.matchList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.afaneca.afascore.ui.model.MatchUiModel
import com.afaneca.afascore.ui.model.ScoreboardUiModel
import com.afaneca.afascore.ui.model.TeamUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */

@Composable
fun MatchListItem(
    match: MatchUiModel
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).background(Color.Blue).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ){
            TeamItem(match.team1)
        }
        Column(
            modifier = Modifier.weight(1f).background(Color.Green).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            match.scoreboard?.let { ScoreboardItem(it) }
        }
        Column(
            modifier = Modifier.weight(1f).background(Color.Red).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            TeamItem(match.team2)
        }
    }
}

@Composable
fun TeamItem(
    team: TeamUiModel
) {
    Text(text = team.fullName ?: "?", maxLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
}

@Composable
fun ScoreboardItem(
    scoreboard: ScoreboardUiModel
) {
    Text(text = "${scoreboard.team1Score} - ${scoreboard.team2Score}", textAlign = TextAlign.Center)
}


@Preview
@Composable
fun MatchListItemPreview() {
    return MatchListItem(
        match = MatchUiModel(
            "1", TeamUiModel("FC Pampilhosa", "FCP", null),
            TeamUiModel("GD Calvão", "GDC", null), "ONGOING", ScoreboardUiModel(1, 1)
        )
    )
}