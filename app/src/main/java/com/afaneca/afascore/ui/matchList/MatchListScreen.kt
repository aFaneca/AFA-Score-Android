package com.afaneca.afascore.ui.matchList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.afaneca.afascore.ui.components.AppBarAction
import com.afaneca.afascore.ui.matchList.components.EmptyView
import com.afaneca.afascore.ui.matchList.components.FilterBottomSheetLayout
import com.afaneca.afascore.ui.matchList.components.MatchListItem
import com.afaneca.afascore.ui.model.MatchUiModel

/**
 * Created by António Faneca on 2/13/2023.
 */
@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchListViewModel = hiltViewModel(),
    setActionOnClick: ((action: AppBarAction) -> Unit) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        setActionOnClick {
            when (it) {
                is AppBarAction.RefreshAction -> viewModel.onRefreshClicked()
                is AppBarAction.FilterAction -> {
                    viewModel.onFilterClicked()
                }

                else -> {}
            }
        }
    }

    if (state.isLoading) {
        LoadingView()
    } else if (state.matchList != null) {
        MatchList(state.matchList!!)
    }

    if (state.isFiltering && state.filterData != null) {
        FilterBottomSheetLayout(
            filterDataUiModel = state.filterData!!,
            onFilterClick = { teams, competitions, statuses ->
                viewModel.onFilterApplyClicked(teams, competitions, statuses)
            },
            onDismiss = { viewModel.onFilterDismiss() })
    }
}

@Composable
fun MatchList(matchList: List<MatchUiModel>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (matchList.isEmpty()) {
            EmptyView()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(matchList) { match ->
                    MatchListItem(match = match)
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}