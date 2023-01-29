package com.example.p2pchat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p2pchat.composables.Center
import com.example.p2pchat.ui.theme.P2pChatTheme
import com.example.p2pchat.ui.theme.fourthColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2pChatTheme {
                val composableScope = rememberCoroutineScope()
                Surface(color = MaterialTheme.colors.background) {
                    SplashView()
                    composableScope.launch{
                        delay(1000)
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SplashView(){
    Center(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.chaticon),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(0.5f),
            colorFilter = ColorFilter.tint(fourthColor)
        )
    }
}