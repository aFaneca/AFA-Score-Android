package com.afaneca.afascore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afaneca.afascore.ui.components.AppBar
import com.afaneca.afascore.ui.components.AppBarAction
import com.afaneca.afascore.ui.matchList.MatchListScreen
import com.afaneca.afascore.ui.theme.AFAScoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val (actionOnClick, setActionOnClick) = remember { mutableStateOf<((action: AppBarAction) -> Unit)?>(null) }
            AFAScoreTheme {
                Scaffold(
                    topBar = { AppBar(this) { action -> actionOnClick?.invoke(action) } },
                    content = { innerPadding ->
                        AppBody(
                            navController,
                            innerPadding,
                            setActionOnClick
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun AppBody(
        navController: NavHostController,
        innerPadding: PaddingValues,
        setActionOnClick: (((action: AppBarAction) -> Unit)?) -> Unit
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.MatchListScreen.route
            ) {
                composable(
                    route = Screen.MatchListScreen.route
                ) { MatchListScreen(navController, setActionOnClick = setActionOnClick) }
            }
        }
    }
}