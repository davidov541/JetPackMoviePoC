package com.example.jetpackmoviepoc

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.example.jetpackmoviepoc.ui.theme.JetPackMoviePoCTheme
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultDataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackMoviePoCTheme {
//                GreetingSurface(MaterialTheme.colorScheme.background, "Android2")
                MovieSurface(MaterialTheme.colorScheme.background, "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_10mb.mp4")
            }
        }
    }
}

@Composable
fun GreetingSurface(background: Color, name: String) {
    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = background) {
        Greeting(name)
    }
}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun MovieSurface(background: Color, videoUri: String) {
    // A surface container using the 'background' color from the theme
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        val mContext = LocalContext.current

        val exoPlayer = remember(mContext) {
            ExoPlayer.Builder(mContext).build().apply {
                val dataSourceFactory = DefaultDataSource.Factory(mContext)
                val mediaItem = MediaItem.fromUri(videoUri)
                val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
                setMediaSource(source)
                prepare()
                play()
            }
        }

        AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetPackMoviePoCTheme {
        Greeting("Android")
    }
}