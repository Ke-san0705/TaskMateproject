package com.kenshin.taskmateproject

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kenshin.taskmateproject.ui.theme.TaskMateprojectTheme
import android.content.Intent

class MainTitle : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMateprojectTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("logo") }

    when (currentScreen) {
        "logo" -> LogoScreen(onStartClick = { currentScreen = "welcome" })
        "welcome" -> WelcomeScreen(onFinish = {
            context.startActivity(Intent(context, KaliLogin::class.java))
        })
    }
}

@Composable
fun LogoScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            TaskMateLogo(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("スタート")
            }
        }
    }
}

@Composable
fun TaskMateLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        try {
            context.assets.open("Picture/TaskMate_Logo.png").use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageBitmap = bitmap?.asImageBitmap()
            }
        } catch (e: Exception) {
            Log.e("ImageLoadDebug", "Error loading logo: ${e.message}")
        }
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap!!,
            contentDescription = "TaskMate Logo",
            modifier = modifier
        )
    } else {
        Box(
            modifier = modifier
                .background(Color.LightGray)
                .fillMaxSize()
        )
    }
}
