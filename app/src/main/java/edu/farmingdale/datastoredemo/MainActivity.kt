package edu.farmingdale.datastoredemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.farmingdale.datastoredemo.ui.EmojiReleaseApp
import edu.farmingdale.datastoredemo.ui.theme.DataStoreDemoTheme

class MainActivity : ComponentActivity() {
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferencesRepository = (application as EmojiReleaseApplication).userPreferencesRepository

        setContent {
            DataStoreDemoTheme {
                EmojiReleaseApp(userPreferencesRepository)
            }
        }
    }
}

@Composable
fun EmojiReleaseApp(userPreferencesRepository: UserPreferencesRepository) {
    var isDarkMode by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Load saved theme preference
    LaunchedEffect(Unit) {
        userPreferencesRepository.isDarkMode.collect { darkMode ->
            isDarkMode = darkMode
        }
    }

    // Apply dark mode
    MaterialTheme {
        Scaffold { paddingValues ->
            Column(
                    modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
            ) {
                // Switch for dark mode
                Switch(
                        checked = isDarkMode,
                        onCheckedChange = { checked ->
                            isDarkMode = checked
                            coroutineScope.launch {
                                userPreferencesRepository.saveDarkModeSetting(checked)
                            }
                        }
                )
                Text(text = "Dark Mode")
                Spacer(modifier = Modifier.height(16.dp))

                // Display emojis
                EmojiList(onEmojiClick = { emoji ->
                    Toast.makeText(LocalContext.current, "You clicked: $emoji", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}

@Composable
fun EmojiList(onEmojiClick: (String) -> Unit) {
    val emojis = listOf("😀", "😂", "😍", "🥳", "😎" "") // Add more emojis as needed
    Column {
        for (emoji in emojis) {
            Text(
                    text = emoji,
                    modifier = Modifier
                            .padding(8.dp)
                            .clickable { onEmojiClick(emoji) }
            )
        }
    }
}