package com.afaneca.afascore.ui.matchList.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.afaneca.afascore.R
import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.ui.components.BottomSheetLayout
import com.afaneca.afascore.ui.matchList.FilterDataUiModel
import com.afaneca.afascore.ui.matchList.FilterableCompetitionUiModel
import com.afaneca.afascore.ui.matchList.FilterableStatusUiModel
import com.afaneca.afascore.ui.matchList.FilterableTeamUiModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by António Faneca on 2/15/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetLayout(
    filterDataUiModel: FilterDataUiModel,
    isToShow: Boolean = true,
    onFilterClick: (
        teams: List<FilterableTeamUiModel>, competitions: List<FilterableCompetitionUiModel>,
        statuses: List<FilterableStatusUiModel>
    ) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheetLayout(
        isToShow = isToShow,
        content = { sheetState ->
            FilterBottomSheetView(
                sheetState,
                filterDataUiModel,
                onFilterClick
            )
        },
        onDismiss = { onDismiss() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterBottomSheetView(
    sheetState: SheetState,
    filterDataUiModel: FilterDataUiModel,
    onFilterClick: (teams: List<FilterableTeamUiModel>, competitions: List<FilterableCompetitionUiModel>, statuses: List<FilterableStatusUiModel>) -> Unit,

    ) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf(
        Pair(R.drawable.team_24dp, R.string.filter_by_team),
        Pair(R.drawable.trophy_24dp, R.string.filter_by_competitions),
        Pair(R.drawable.whistle_24dp, R.string.filter_by_status),
    )
    val (teams, setTeams) = remember {
        mutableStateOf(filterDataUiModel.teamsList)
    }

    val (competitions, setCompetitions) = remember {
        mutableStateOf(filterDataUiModel.competitionsList)
    }
    val (statuses, setStatuses) = remember {
        mutableStateOf(filterDataUiModel.statusList)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                onFilterClick(teams, competitions, statuses)
                coroutineScope.launch { sheetState.hide() }
            }) {
            Text(text = stringResource(id = R.string.filter))
        }
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, tabData ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Icon(
                            painter = painterResource(id = tabData.first),
                            contentDescription = stringResource(id = tabData.second),
                        )
                    })
            }
        }

        when (selectedTabIndex) {
            0 -> {
                TeamFilterView(teams) { updatedTeam ->
                    setTeams(teams.map { team ->
                        if (team.name == updatedTeam.name) updatedTeam
                        else team
                    })
                }
            }

            1 -> {
                CompetitionFilterView(competitions) { updatedComp ->
                    setCompetitions(competitions.map { comp ->
                        if (comp.name == updatedComp.name) updatedComp
                        else comp
                    })
                }
            }

            else -> {
                StatusFilterView(statuses) { updatedStatus ->
                    setStatuses(statuses.map { status ->
                        if (status.status == updatedStatus.status) updatedStatus
                        else status
                    })
                }
            }
        }
    }
}

@Composable
fun TeamFilterView(
    teams: List<FilterableTeamUiModel>,
    onValueChanged: (team: FilterableTeamUiModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(teams) { team ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = team.isSelected,
                            onValueChange = { onValueChanged(team.copy(isSelected = it)) },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = team.isSelected,
                        onCheckedChange = null
                    )
                    Text(
                        text = team.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CompetitionFilterView(
    competitions: List<FilterableCompetitionUiModel>,
    onValueChanged: (comp: FilterableCompetitionUiModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(competitions) { team ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = team.isSelected,
                            onValueChange = { onValueChanged(team.copy(isSelected = it)) },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = team.isSelected,
                        onCheckedChange = null
                    )
                    Text(
                        text = team.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StatusFilterView(
    statuses: List<FilterableStatusUiModel>,
    onValueChanged: (comp: FilterableStatusUiModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(statuses) { status ->
                val statusName = when (status.status) {
                    is Constants.GameStatus.NotStarted -> stringResource(id = R.string.game_status_not_started)
                    is Constants.GameStatus.Ongoing -> stringResource(id = R.string.game_status_ongoing)
                    else -> stringResource(id = R.string.game_status_finished)

                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = status.isSelected,
                            onValueChange = { onValueChanged(status.copy(isSelected = it)) },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = status.isSelected,
                        onCheckedChange = null
                    )
                    Text(
                        text = statusName,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}
