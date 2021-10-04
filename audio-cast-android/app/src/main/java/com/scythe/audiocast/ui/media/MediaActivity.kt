package com.scythe.audiocast.ui.media

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.scythe.audiocast.model.Media
import com.scythe.audiocast.ui.cast.CastActivity
import com.scythe.audiocast.ui.component.EmptyRowComponent
import com.scythe.audiocast.ui.theme.AudioCastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaActivity : ComponentActivity() {

    private val mediaViewModel: MediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioCastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MediaScreen {
                        mediaViewModel.selectMedia(it)
                        Intent(this, CastActivity::class.java).run {
                            startActivity(this)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaScreen(mediaViewModel: MediaViewModel = viewModel(), onClick: (Media) -> Unit) {
    val mediaInputs: List<Media> by mediaViewModel.mediaInputs.observeAsState(initial = emptyList())
    val isRefreshing by mediaViewModel.isRefreshing.collectAsState()
    MediaComponent(mediaInputs, isRefreshing, mediaViewModel::updateList, onClick)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaComponent(
    mediaInputs: List<Media>,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    onClick: (Media) -> Unit
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
            if (mediaInputs.isNotEmpty()) {
                item {
                    ListItem(
                        text = {
                            Text(
                                "Select an input:",
                                fontWeight = FontWeight.Bold
                            )
                        })
                }
                items(mediaInputs) {
                    MediaRowComponent(media = it, onClick)
                }
            } else {
                if (!isRefreshing) {
                    item {
                        EmptyRowComponent("No media inputs found. Swipe to refresh.")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaRowComponent(media: Media, onClick: (Media) -> Unit) {
    ListItem(
        text = { Text(media.name) },
        modifier = Modifier.clickable { onClick(media) })
}

@Preview(
    showBackground = true
)
@Composable
fun MediaScreenPreview() {
    AudioCastTheme {
        MediaScreen {}

    }
}