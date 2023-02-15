package com.afaneca.afascore.ui.matchList.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.afaneca.afascore.ui.components.BottomSheetLayout
import kotlinx.coroutines.launch

/**
 * Created by António Faneca on 2/15/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetLayout(isToShow: Boolean = true, onDismiss: () -> Unit) {
    BottomSheetLayout(
        isToShow = isToShow,
        content = { FilterBottomSheetView() },
        onDismiss = { onDismiss() })
}

@Composable
private fun FilterBottomSheetView() {
    Text(text = "Bottom sheet!")
}