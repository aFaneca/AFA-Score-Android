package com.afaneca.afascore.ui.matchList

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.afaneca.afascore.R
import com.afaneca.afascore.ui.components.AppBarAction
import com.afaneca.afascore.ui.matchList.components.EmptyView
import com.afaneca.afascore.ui.matchList.components.FilterBottomSheetLayout
import com.afaneca.afascore.ui.matchList.components.MatchListItem
import com.afaneca.afascore.ui.model.MatchUiModel
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()

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
    } else if (state.filteredMatchList != null) {
        val context = LocalContext.current
        val favoriteStartMessage = stringResource(R.string.favorite_start_toast_message)
        val favoriteStopMessage = stringResource(R.string.favorite_stop_toast_message)
        if (state.isSneakyLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        MatchList(
            matchList = state.filteredMatchList!!,
            hasFilters = state.matchList?.size != state.filteredMatchList!!.size,
            onResetClick = { viewModel.resetFilters() },
            onToggleFavoriteClick = { match ->
                if (match.isFavorite) {
                    // Will no longer be a favorite
                    Toast.makeText(context, favoriteStopMessage, Toast.LENGTH_SHORT).show()
                } else {
                    // Is now a favorite
                    Toast.makeText(context, favoriteStartMessage, Toast.LENGTH_SHORT).show()
                }
                viewModel.toggleFavorite(match)
            }
        )
    }

    if (state.isFiltering && state.filterData != null) {
        FilterBottomSheetLayout(
            filterDataUiModel = state.filterData!!,
            onFilterClick = { teams, competitions, statuses ->
                coroutineScope.launch {
                    viewModel.onFilterApplyClicked(
                        teams,
                        competitions,
                        statuses
                    )
                }
            },
            onDismiss = { viewModel.onFilterDismiss() })
    }
}

@Composable
fun MatchList(
    matchList: List<MatchUiModel>,
    hasFilters: Boolean,
    onResetClick: () -> Unit,
    onToggleFavoriteClick: (match: MatchUiModel) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (matchList.isEmpty()) {
            EmptyView(hasFilters, onResetClick)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(matchList) { match ->
                    MatchListItem(match = match, onToggleFavoriteClick)
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