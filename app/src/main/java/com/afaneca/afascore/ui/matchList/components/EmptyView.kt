package com.afaneca.afascore.ui.matchList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.afaneca.afascore.R

/**
 * Created by António Faneca on 2/14/2023.
 */

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.soccer_ball), "",
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(1f),
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        )
        Text(text = stringResource(id = R.string.game_list_empty),
        modifier = Modifier.padding(30.dp))
    }

}