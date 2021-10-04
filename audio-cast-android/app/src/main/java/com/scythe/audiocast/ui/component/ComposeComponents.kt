package com.scythe.audiocast.ui.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyRowComponent(text: String) {
    ListItem(
        text = { Text(text) }
    )
}