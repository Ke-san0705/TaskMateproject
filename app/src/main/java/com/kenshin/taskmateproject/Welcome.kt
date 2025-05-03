package com.kenshin.taskmateproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenshin.taskmateproject.ui.theme.TaskMateprojectTheme
import kotlinx.coroutines.delay

class Welcome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMateprojectTheme {
                WelcomeScreen(
                    onFinish = {
                        startActivity(Intent(this, KaliLogin::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(onFinish: () -> Unit) {
    val messages = listOf(
        "TaskMateへようこそ",
        "TaskMateは",
        "あなたの毎日をちょっと楽しくするタスク管理アプリです。",
        "応援してくれるのは、友人や気になるあの子。",
        "あるいは、偉人のようなAIアバターかもしれません。",
        "やるべきことに、少しの勇気とモチベーションを。",
        "1つずつこなして、積み重なる達成感を。",
        "さあ、理想の自分に近づこう。"
    )

    var index by remember { mutableStateOf(0) }
    var showMessage by remember { mutableStateOf(true) }
    var triggerNextMessage by remember { mutableStateOf(false) }

    val isLastMessage = index == messages.lastIndex
    val context = LocalContext.current

    // メッセージを1つ進める処理
    LaunchedEffect(triggerNextMessage) {
        if (triggerNextMessage && !isLastMessage) {
            delay(500)
            index++
            showMessage = true
            triggerNextMessage = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!isLastMessage) {
                    showMessage = false
                    triggerNextMessage = true
                } else {
                    onFinish()
                }
            }
            .padding(32.dp)
    ) {
        // スキップボタン（右上）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "スキップ",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    index = messages.lastIndex
                    showMessage = true
                }
            )
        }

        // メッセージ表示エリア
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showMessage) {
                AnimatedText(messages[index])
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (isLastMessage) "タップしてスタート" else "タップして次へ",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AnimatedText(message: String) {
    val transition = remember { mutableStateOf("") }
    val animatedText = message.chunked(1)

    LaunchedEffect(message) {
        transition.value = ""
        for (char in animatedText) {
            transition.value += char
            delay(100)
        }
    }

    Text(
        text = transition.value,
        fontSize = 22.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(horizontal = 8.dp)
    )
}
