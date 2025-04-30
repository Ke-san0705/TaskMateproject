package com.kenshin.taskmateproject

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

class Welcome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMateprojectTheme {
                WelcomeScreen(onFinish = {
                    startActivity(Intent(this, KaliLogin::class.java))
                    })
            }
        }
    }
}

@Composable
fun WelcomeScreen(onFinish: () -> Unit) {
    val context = LocalContext.current

    val messages = listOf(
        "TaskMateは、あなたの毎日をちょっと楽しくするタスク管理アプリです。",
        "応援してくれるのは、友人や気になるあの子。",
        "あるいは、偉人のようなAIアバターかもしれません。",
        "やるべきことに、少しの勇気とモチベーションを。",
        "1つずつこなして、積み重なる達成感を。",
        "さあ、理想の自分に近づこう。"
    )

    var index by remember { mutableStateOf(0) }
    val isLastMessage = index == messages.lastIndex

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!isLastMessage) {
                    index++
                } else {
                    // 遷移先（KaliLogin）へ
                    context.startActivity(Intent(context, KaliLogin::class.java))
                    if (context is ComponentActivity) context.finish()
                }
            }
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = messages[index],
                fontSize = 22.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (isLastMessage) "タップしてスタート" else "タップして次へ",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
