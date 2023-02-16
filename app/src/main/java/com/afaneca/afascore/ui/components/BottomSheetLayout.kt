package com.afaneca.afascore.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * Created by António Faneca on 2/15/2023.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayout(
    isToShow: Boolean = true,
    content: @Composable() (ColumnScope.() -> Unit),
    onDismiss: () -> Unit
) {
    val sheetState = rememberSheetState(
        skipHalfExpanded = false,
        /*confirmValueChange = { it != SheetValue.Collapsed }*/
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
        onDismiss()
    }

    ModalBottomSheet(
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { onDismiss() },
        content = { content() },
    )

    LaunchedEffect(key1 = isToShow) {
        if (isToShow) sheetState.show()
        else sheetState.hide()
    }
}