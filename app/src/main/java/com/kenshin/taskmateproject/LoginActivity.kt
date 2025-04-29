package com.kenshin.taskmateproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


class LoginActivity : ComponentActivity() {

    // FirebaseAuthのインスタンスを取得
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // FirebaseAuthを初期化
        auth = FirebaseAuth.getInstance()

        setContent {
            LoginScreen(auth)
        }
    }
}

@Composable
fun LoginScreen(auth: FirebaseAuth) {
    // コンテキストを取得（Toastなどに使う）
    val context = LocalContext.current

    // 入力されたメールアドレスとパスワードを保持する状態
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 画面UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "ログイン", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // メール入力欄
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("メールアドレス") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // パスワード入力欄
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("パスワード") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ログインボタン
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "ログイン成功", Toast.LENGTH_SHORT).show()
                            // ここでMainActivityなどに遷移させる予定（次で書く）
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "ログイン失敗: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ログイン")
        }
    }
}
