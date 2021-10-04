package com.scythe.audiocast.ui.cast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.scythe.audiocast.model.Device
import com.scythe.audiocast.ui.theme.AudioCastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioCastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CastScreen()
                }
            }
        }
    }
}

@Composable
fun CastScreen(castViewModel: CastViewModel = viewModel()) {
    val devices: List<Device> by castViewModel.devices.observeAsState(initial = emptyList())
    val isRefreshing by castViewModel.isRefreshing.collectAsState()
    CastComponent(devices, isRefreshing, castViewModel::updateList, castViewModel::selectDevice)
}

@Composable
fun CastComponent(
    devices: List<Device>,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    onClick: (Device) -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { refresh() },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (devices.isNotEmpty()) {
                items(devices) {
                    DeviceRowComponent(device = it, onClick)
                }
            } else {
                if (!isRefreshing) {
                    item {
                        EmptyRowComponent()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceRowComponent(device: Device, onClick: (Device) -> Unit) {
    ListItem(
        text = { Text(device.name) },
        secondaryText = { Text(device.type) },
        modifier = Modifier.clickable { onClick(device) })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyRowComponent() {
    ListItem(
        text = { Text("No devices found. Swipe to refresh.") }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun CastScreenPreview() {
    AudioCastTheme {
        CastScreen()

    }
}