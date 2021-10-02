package com.scythe.audiocast.ui.welcome

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scythe.audiocast.R
import com.scythe.audiocast.ui.cast.CastActivity
import com.scythe.audiocast.ui.theme.AudioCastTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioCastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WelcomeScreen(saveServer = {
                        val success = viewModel.saveServer()
                        if (success) {
                            Intent(this, CastActivity::class.java).run {
                                startActivity(this)
                            }
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun WelcomeScreen(welcomeViewModel: WelcomeViewModel = viewModel(), saveServer: () -> Unit) {
    val ipAddress: String by welcomeViewModel.serverIpAddress.observeAsState("")
    val port: String by welcomeViewModel.serverPort.observeAsState("")
    WelcomeContent(
        ipAddress = ipAddress,
        port = port,
        onServerChange = { welcomeViewModel.onServerChange(it) },
        saveServer = saveServer
    )
}

@ExperimentalAnimationApi
@Composable
fun WelcomeContent(
    ipAddress: String,
    port: String,
    onServerChange: (String) -> Unit,
    saveServer: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        var clicked by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        val (image, button, textField) = createRefs()
        val barrier = createTopBarrier(button, textField)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Just a logo",
            modifier = Modifier.constrainAs(image) {
                centerHorizontallyTo(parent)
                bottom.linkTo(barrier)
            }
        )
        AnimatedVisibility(!clicked,
            modifier = Modifier.constrainAs(button) {
                centerTo(parent)
            }) {
            Button(onClick = { clicked = true }) {
                Text(text = "Add server")
            }
        }
        AnimatedVisibility(clicked,
            modifier = Modifier.constrainAs(textField) {
                centerTo(parent)
            }) {
            // var serverDetails by remember { mutableStateOf("") }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = "$ipAddress:$port",
                    modifier = Modifier
                        .focusRequester(focusRequester),
                    onValueChange = onServerChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        autoCorrect = false
                    ),
                    label = { Text(text = "Server details") },
                    placeholder = { Text(text = "ip_address:port") },
                    trailingIcon = {
                        Image(
                            modifier = Modifier.clickable {
                                clicked = false
                                onServerChange(":")
                            },
                            painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                            contentDescription = "Cancel button"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    saveServer()
                }) {
                    Text(text = "Ok")
                }
            }
            DisposableEffect(this) {
                focusRequester.requestFocus()
                onDispose { }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun DefaultPreview() {
    AudioCastTheme {
        WelcomeContent("192.168.1.10", "5551", {}, {})
    }
}