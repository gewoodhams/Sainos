package com.example.sainos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.sainos.ui.theme.SainosTheme
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.milliseconds
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

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
    val context = LocalContext.current
    val dynamicBackgroundBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.7f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha=0.5f),
            MaterialTheme.colorScheme.background
        )
    )

    if (state == "main-menu") {
        Box(modifier = modifier.fillMaxSize().padding(all = 16.dp)) {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Sainos", style = MaterialTheme.typography.headlineSmall)
                Row{FilledTonalButton(onClick = {state = "closing-script"}) {Text(text="Closing Script")}; FilledTonalButton(onClick = {state = "cold-chain"}) {Text(text="Cold Chain")};FilledTonalButton(onClick = {state = "speed-dial"}) {Text(text="Speed-Dial")} }
            }
        }
    }

    else if (state == "closing-script") {
        var currentTime by remember { mutableStateOf(LocalDateTime.now()) }
        var doorsLocked by remember { mutableStateOf("false") }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000.milliseconds)
                currentTime = LocalDateTime.now()
            }
        }

        val formattedTime = currentTime.format(DateTimeFormatter.ofPattern("h mm a"))
            .lowercase()
            .replace(" 00", "")

        val dayName = currentTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        val monthName = currentTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val day = currentTime.dayOfMonth
        val year = currentTime.year

        val suffix = when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }

        val closingTime = when(dayName){
            "Sunday" -> LocalTime.of(16, 0)
            else -> LocalTime.of(22, 0)
        }
        val minutesLeft = ChronoUnit.MINUTES.between(currentTime.toLocalTime(), closingTime)

        val doorAnnouncement = when (doorsLocked) {
            "left" -> " The left hand doors have now been locked at the exit, please exit through the right side."
            "right" -> " The right hand doors have now been locked at the exit, please exit through the left side."
            else -> ""
        }

        val announcementText = if (minutesLeft > 0) {
            """
        Good evening.
        
        The time is currently $formattedTime on $dayName, the $day$suffix of $monthName $year, and Sainsbury's, will be closed in $minutesLeft minutes, at 10pm.
        
        Make your final selections and continue to our self service checkouts.
        
        $doorAnnouncement
        
        Thank you for shopping at Sainsbury's, and have a wonderful weekend.
        """.trimIndent()
        } else {
            """
        Good evening.
        
        The time is currently $formattedTime on $dayName the $day$suffix of $monthName, and Sainsbury's, is now closed.
        
        Continue to our self service checkouts.
        
        $doorAnnouncement
        
        Thank you for shopping at Sainsbury's, and have a wonderful weekend.
        """.trimIndent()
        }
        Box(modifier = modifier.fillMaxSize().padding(all = 16.dp), contentAlignment = Alignment.BottomCenter) {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                FilledTonalButton(onClick = { doorsLocked = "false" }) { Text(text = ("All doors open.")) }
                Row {
                    OutlinedButton(onClick = { doorsLocked = "left" }) { Text(text = ("Left doors locked.")) }
                    OutlinedButton(onClick = { doorsLocked = "right" }) { Text(text = ("Right doors locked.")) }
                }
                Column { Text(text = announcementText) }
            }
        }
    }

    else if (state == "cold-chain") {
        Box(modifier = modifier.fillMaxSize().padding(all = 16.dp), contentAlignment = Alignment.CenterStart) {

        }
    } else if (state == "speed-dial") {
        Box(modifier = modifier.fillMaxSize().padding(all = 16.dp), contentAlignment = Alignment.CenterStart) {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){Button(onClick = {
                val intent = Intent(Intent.ACTION_DIAL, "tel:01234567890".toUri())
                context.startActivity(intent)
            }) {
                Text("Open 01234567890")
            } }
        }
    }
}

