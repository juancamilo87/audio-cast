package com.scythe.audiocast.ui.casting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scythe.audiocast.model.Device
import com.scythe.audiocast.model.Media
import com.scythe.audiocast.ui.component.EmptyRowComponent
import com.scythe.audiocast.ui.theme.AudioCastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioCastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CastingScreen()
                }
            }
        }
    }
}

@Composable
fun CastingScreen(castingViewModel: CastingViewModel = viewModel()) {
    val media = castingViewModel.media
    val castDevice = castingViewModel.castDevice
    CastingComponent(media, castDevice, castingViewModel::cast, castingViewModel::stop)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CastingComponent(
    media: Media?,
    castDevice: Device?,
    cast: () -> Unit,
    stop: () -> Unit
) {
    val confCorrect = media != null && castDevice != null
    var casting by remember { mutableStateOf(false) }
    if (confCorrect) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Item("Media:", media!!.name)
            Item("Casting to:", castDevice!!.name)
            Crossfade(targetState = casting) {
                when (it) {
                    true -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Button(onClick = {
                                casting = false
                                stop()
                            }) {
                                Text(text = "Stop")
                            }
                        }
                    }
                    false -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Button(onClick = {
                                casting = true
                                cast()
                            }) {
                                Text(text = "Start casting")
                            }
                        }
                    }
                }
            }
        }
    } else {
        EmptyRowComponent(text = "Wrong configuration.")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Item(text: String, secondaryText: String) {
    ListItem(
        text = { Text(text) },
        secondaryText = { Text(secondaryText) })
}

@Preview(
    showBackground = true
)
@Composable
fun CastingScreenPreview() {
    AudioCastTheme {
        CastingComponent(
            Media("f"),
            Device("w", "e", 123, "dsa", "ds"),
            {},
            {})

    }
}