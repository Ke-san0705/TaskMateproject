package com.kenshin.taskmateproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenshin.taskmateproject.ui.theme.TaskMateprojectTheme

class MainMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMateprojectTheme {
                MainScreen()
            }
        }
    }
}

// ナビゲーションメニューのデータ構造
data class MenuItem(val label: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val menuItems = listOf(
        MenuItem("ホーム", Icons.Filled.Home),
        MenuItem("タスク作成", Icons.Filled.AddTask),
        MenuItem("タスク一覧", Icons.Filled.List),
        MenuItem("アバター", Icons.Filled.EmojiPeople),
        MenuItem("通知", Icons.Filled.Notifications),
        MenuItem("フレンド", Icons.Filled.Group)
    )

    var selectedItem by remember { mutableStateOf(0) }
    var isSettingsOpen by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var fontSizePx by remember { mutableStateOf(16f) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "TaskMate",
                        modifier = Modifier.clickable {
                            // MainTitle.kt に遷移
                            context.startActivity(Intent(context, MainTitle::class.java))
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = { isSettingsOpen = true }) {
                        Icon(Icons.Filled.Settings, contentDescription = "設定")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                menuItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label, fontSize = fontSizePx.sp) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "選択中のメニュー: ${menuItems[selectedItem].label}",
                fontSize = fontSizePx.sp
            )
        }

        if (isSettingsOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSettingsOpen = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("設定", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(16.dp))

                    // 通知スイッチ
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("通知", modifier = Modifier.weight(1f))
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // フォントサイズ調整
                    Text("フォントサイズ: ${fontSizePx.toInt()} px")
                    Slider(
                        value = fontSizePx,
                        onValueChange = { fontSizePx = it },
                        valueRange = 10f..30f
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // ログアウト
                    Button(
                        onClick = {
                            // ログアウト処理はここに実装
                            isSettingsOpen = false
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(Icons.Filled.Logout, contentDescription = "ログアウト")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ログアウト")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
