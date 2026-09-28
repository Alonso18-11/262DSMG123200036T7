package com.example.racetracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.racetracker.R
import com.example.racetracker.ui.theme.RaceTrackerTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun RaceTrackerApp() {
    // remember conserva a los participantes entre recomposiciones
    val playerOne = remember { RaceParticipant(name = "Player 1", progressIncrement = 1) }
    val playerTwo = remember { RaceParticipant(name = "Player 2", progressIncrement = 2) }
    var raceInProgress by remember { mutableStateOf(false) }

    if (raceInProgress) {
        // LaunchedEffect ejecuta corrutinas atadas al ciclo de vida del elemento
        // componible. Si raceInProgress pasa a false, la corrutina se cancela (pausa).
        LaunchedEffect(playerOne, playerTwo) {
            // coroutineScope espera a que ambos hijos terminen (simultaneidad estructurada)
            coroutineScope {
                launch { playerOne.run() }
                launch { playerTwo.run() }
            }
            raceInProgress = false // Solo llega aquí cuando ambos terminaron
        }
    }

    RaceTrackerScreen(
        playerOne = playerOne,
        playerTwo = playerTwo,
        isRunning = raceInProgress,
        onRunStateChange = { raceInProgress = it },
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun RaceTrackerScreen(
    playerOne: RaceParticipant,
    playerTwo: RaceParticipant,
    isRunning: Boolean,
    onRunStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.run_a_race),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text(text = "\uD83C\uDFC3", fontSize = 72.sp, modifier = Modifier.padding(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StatusIndicator(playerOne)
                StatusIndicator(playerTwo)
                RaceControls(
                    isRunning = isRunning,
                    onRunStateChange = onRunStateChange,
                    onReset = {
                        playerOne.reset()
                        playerTwo.reset()
                        onRunStateChange(false)
                    }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun StatusIndicator(participant: RaceParticipant, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = participant.name, modifier = Modifier.width(80.dp))
        Column(modifier = Modifier.weight(1f)) {
            LinearProgressIndicator(
                progress = { participant.progressFactor },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            Text(
                text = stringResource(
                    R.string.progress_percentage,
                    participant.currentProgress,
                    participant.maxProgress
                ),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun RaceControls(
    isRunning: Boolean,
    onRunStateChange: (Boolean) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onRunStateChange(!isRunning) }, modifier = Modifier.fillMaxWidth()) {
            Text(if (isRunning) stringResource(R.string.pause) else stringResource(R.string.start))
        }
        OutlinedButton(onClick = onReset, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.reset))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceTrackerAppPreview() {
    RaceTrackerTheme {
        RaceTrackerApp()
    }
}
