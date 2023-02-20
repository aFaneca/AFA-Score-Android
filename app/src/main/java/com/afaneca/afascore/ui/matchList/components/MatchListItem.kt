package com.afaneca.afascore.ui.matchList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.afaneca.afascore.R
import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.ui.model.MatchUiModel
import com.afaneca.afascore.ui.model.ScoreboardUiModel
import com.afaneca.afascore.ui.model.TeamUiModel
import com.afaneca.afascore.ui.theme.Colors
import com.afaneca.afascore.ui.theme.Typography
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

/**
 * Created by António Faneca on 2/13/2023.
 */

@Composable
fun MatchListItem(
    match: MatchUiModel
) {
    val (isSelected, setSelected) = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .clickable { setSelected(!isSelected) }
                .run {
                    if (!isSelected) this
                    else {
                        background(
                            /*MaterialTheme.colorScheme.secondary*/
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Colors.Green,
                                    Colors.DarkGreen
                                )
                            )
                        )
                    }
                }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "${match.leagueDivision} (${match.startDate})",
                    style = Typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                GameStatus(match.status)
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                /* TEAM 1 */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    TeamItem(match.team1)
                }
                /* SCORE */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        /*.background(MaterialTheme.colorScheme.secondaryContainer)*/
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Box(modifier = Modifier.padding(20.dp)) {
                            when (match.status) {
                                is Constants.GameStatus.NotStarted -> {
                                    GameTime(match.startTime ?: "?")
                                }

                                is Constants.GameStatus.Ongoing -> {
                                    match.scoreboard?.let { ScoreboardItem(it) }
                                }

                                is Constants.GameStatus.Finished -> {
                                    match.scoreboard?.let { ScoreboardItem(it) }
                                }

                                else -> {}
                            }
                        }
                    }
                }
                /* TEAM 2*/
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    TeamItem(match.team2)
                }
            }
        }
    }
}

@Composable
fun GameStatus(
    matchStatus: Constants.GameStatus
) {
    var statusText = ""
    var bgColor = Color.Transparent
    when (matchStatus) {
        Constants.GameStatus.NotStarted -> {
            statusText = stringResource(id = R.string.game_status_not_started)
            bgColor = Colors.Purple40
        }

        Constants.GameStatus.Ongoing -> {
            statusText = stringResource(id = R.string.game_status_ongoing)
            bgColor = Colors.DarkGreen
        }

        Constants.GameStatus.Finished -> {
            statusText = stringResource(id = R.string.game_status_finished)
            bgColor = Colors.Pink40
        }

        else -> {}
    }
    Card(
        border = BorderStroke(2.dp, bgColor),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = statusText,
            modifier = Modifier.padding(10.dp),
            style = Typography.labelSmall,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TeamItem(
    team: TeamUiModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GlideImage(
            modifier = Modifier
                .height(50.dp)
                .aspectRatio(1f),
            imageModel = { team.logoUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            ),
        )
        /*Image(
            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
            contentDescription = "",
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f),
            alignment = Alignment.Center,
        )*/
        Text(
            text = team.fullName ?: "?",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = Typography.labelMedium
        )
    }
}

@Composable
fun ScoreboardItem(
    scoreboard: ScoreboardUiModel
) {
    Text(
        text = "${scoreboard.team1Score} - ${scoreboard.team2Score}", textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
fun GameTime(
    startTime: String
) {
    Text(text = startTime, textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MatchListItemPreview() {
    MatchListItem(
        match = MatchUiModel(
            "1",
            TeamUiModel("FC Pampilhosa", "FCP", null),
            TeamUiModel("GD Calvão", "GDC", null),
            Constants.GameStatus.Ongoing,
            ScoreboardUiModel(1, 1),
            "",
            "",
            ""
        )
    )
}