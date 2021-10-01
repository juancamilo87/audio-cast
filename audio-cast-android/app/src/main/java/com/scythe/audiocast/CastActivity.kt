package com.scythe.audiocast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.scythe.audiocast.ui.theme.AudioCastTheme

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
fun CastScreen() {
    //TODO: List of chromecasts to cast to
    Text(text = "Something!")
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