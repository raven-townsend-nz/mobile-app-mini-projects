package nz.ac.canterbury.rto45.twotimerwithcompose

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import nz.ac.canterbury.rto45.twotimerwithcompose.ui.theme.TwoTimerWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val t1 = TimeZone.getTimeZone("Pacific/Auckland")
        val t2 = TimeZone.getTimeZone("America/Chicago")

        setContent {
            Column(Modifier.fillMaxHeight()) {
                Box(Modifier.weight(1f)) {
                    TimeDisplay(t1)
                }
                Box(Modifier.weight(1f)) {
                    TimeDisplay(t2)
                }
            }
        }
    }
}


@Composable
fun TimeDisplay(initTimeZone: TimeZone) {
    val formatter = SimpleDateFormat("d MMMM HH:mm")
    formatter.timeZone = initTimeZone

    var today = Calendar.getInstance()
    var time by remember { mutableStateOf(formatter.format(today.time)) }
    Text(time)

    LaunchedEffect(Unit) {
        while(true) {
            today = Calendar.getInstance()
            time = formatter.format(today.time)
            delay(1000)
        }
    }

    var expanded by remember { mutableStateOf(true) }
    val timeZones = TimeZone.getAvailableIDs()
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }) {
        timeZones.forEach { item ->
            DropdownMenuItem(onClick = {
                formatter.timeZone = TimeZone.getTimeZone(item)
                expanded = false
            }) {
                Text(text = item)
            }
        }
    }
}

