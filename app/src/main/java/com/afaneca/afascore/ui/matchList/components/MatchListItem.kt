package com.afaneca.afascore.ui.matchList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    match: MatchUiModel,
    onToggleFavoriteClick: (match: MatchUiModel) -> Unit,
) {
    Card(
        modifier = Modifier.padding(10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .clickable { /*setSelected(!isSelected)*/ }
                .run {
                    if (!match.hasRecentActivity) this
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
                /* COMPETITION / DATE */
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "${match.leagueDivision} (${match.startDate})",
                    style = Typography.labelSmall,
                    color = if (match.hasRecentActivity) Color.White else MaterialTheme.colorScheme.onSurface,
                )
                /* GAME STATUS / NOTIFICATION CTA */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    /*horizontalArrangement = Arrangement.Center*/
                ) {
                    Spacer(Modifier.weight(1f))
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GameStatus(match.status)
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            imageVector = if (match.isFavorite) Icons.Filled.NotificationsOff else Icons.Outlined.NotificationAdd,
                            contentDescription = "",
                            modifier = Modifier.clickable { onToggleFavoriteClick(match) }
                        )
                    }

                }

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
                    TeamItem(match.team1, match.hasRecentActivity)
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
                    TeamItem(match.team2, match.hasRecentActivity)
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
    team: TeamUiModel,
    hasRecentActivity: Boolean
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
            style = Typography.labelMedium,
            color = if (hasRecentActivity) Color.White else MaterialTheme.colorScheme.onSurface
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
            "",
            false,
            false
        ),
        {}
    )
}