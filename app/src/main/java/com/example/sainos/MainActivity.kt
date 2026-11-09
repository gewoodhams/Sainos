package com.example.sainos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import com.example.sainos.ui.theme.SainosTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SainosTheme {
                Scaffold(modifier = Modifier) { innerPadding ->
                    Go(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Go(modifier = Modifier.fillMaxSize())
}



@Composable
fun Go(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf("main-menu") }

    if (state == "main-menu") {
        Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
            Column(modifier = modifier) {
                FilledTonalButton(onClick = {state = "closing-script"}) {Text(text="Closing Script")}
            }
        }
    }

    else if (state == "closing-script") {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

            Column {
                Text(text = "Good Evening.")
                Text(text = "The time is currently $currentTime, on Friday $currentDate.")
            }
        }
    }
}

