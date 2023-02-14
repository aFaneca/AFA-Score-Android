package com.afaneca.afascore.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.afaneca.afascore.R

/**
 * Created by António Faneca on 2/13/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController, context: Context) {
    TopAppBar(title = {
        Text(text = context.getString(R.string.app_name))
    },
        actions = {
            // refresh icon
            TopAppBarActionButton(
                imageVector = Icons.Default.Refresh,
                description = context.getString(R.string.refresh),
                onClick = {}
            )
        })
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}