package com.afaneca.afascore.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.afaneca.afascore.R

/**
 * Created by António Faneca on 2/13/2023.
 */

sealed interface AppBarAction {
    object FilterAction : AppBarAction
    object RefreshAction : AppBarAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    context: Context,
    onActionClick: (action: AppBarAction) -> Unit,
) {
    TopAppBar(title = {
        Text(text = context.getString(R.string.app_name))
    },
        actions = {
            // filter icon
            TopAppBarActionButton(
                imageVector = Icons.Default.Search,
                description = context.getString(R.string.filter),
                onClick = { onActionClick(AppBarAction.FilterAction) }
            )
            // refresh icon
            TopAppBarActionButton(
                imageVector = Icons.Default.Refresh,
                description = context.getString(R.string.refresh),
                onClick = { onActionClick(AppBarAction.RefreshAction) }
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