package com.afaneca.afascore.ui.matchList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.afaneca.afascore.ui.matchList.components.MatchListItem

/**
 * Created by António Faneca on 2/13/2023.
 */
@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchListViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        state.matchList?.let { matchList ->
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(matchList){match ->
                    MatchListItem(match = match)
                }
            }
        } ?: run {
            // TOOD - empty view
        }

    }
}