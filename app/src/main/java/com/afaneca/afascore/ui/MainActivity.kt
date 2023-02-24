package com.afaneca.afascore.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.ui.components.AppBar
import com.afaneca.afascore.ui.components.AppBarAction
import com.afaneca.afascore.ui.matchList.MatchListScreen
import com.afaneca.afascore.ui.theme.AFAScoreTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Notifications are now enabled
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val (actionOnClick, setActionOnClick) = remember {
                mutableStateOf<((action: AppBarAction) -> Unit)?>(
                    null
                )
            }
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Timber.d("PushNotification FCM KEY: " + it.result)
            FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_DEFAULT_TOPIC)
        }
        askNotificationPermission()
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Notifications are enabled
            } /*else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            }*/ else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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