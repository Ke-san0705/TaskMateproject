package com.kenshin.taskmateproject

import androidx.compose.runtime.LaunchedEffect
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.animation.core.animateFloatAsState  // アニメーションを使う際に必要
import androidx.compose.runtime.LaunchedEffect           // 非同期の効果（アニメーション実行）に必要
import kotlinx.coroutines.delay                         // 文字を一つずつ表示するために必要


class Welcome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMateprojectTheme {
                WelcomeScreen(onFinish = {
                    startActivity(Intent(this, KaliLogin::class.java))
                    finish()
                })
            }
        }
    }
}

@Composable
fun WelcomeScreen(onFinish: () -> Unit) {
    val messages = listOf(
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

    // メッセージ切り替えのための非同期処理
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
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
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
    val animatedText = message.chunked(1) // 文字を一文字ずつに分ける

    LaunchedEffect(Unit) {
        for (char in animatedText) {
            transition.value += char
            delay(100) // 各文字の間隔（ミリ秒）
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
