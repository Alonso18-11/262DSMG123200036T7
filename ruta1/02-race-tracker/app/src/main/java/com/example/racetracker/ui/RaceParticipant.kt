package com.example.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

/**
 * Representa a un participante de la carrera. Su progreso avanza en [run],
 * que es una función de suspensión: se ejecuta dentro de una corrutina y
 * puede pausarse/cancelarse sin bloquear el subproceso principal.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 500L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) {
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /** Progreso actual. Es estado de Compose para que la IU se recomponga. */
    var currentProgress by mutableStateOf(initialProgress)
        private set

    /** Avanza el progreso hasta llegar a [maxProgress]. */
    suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("RaceParticipant", "$name: ${e.message}")
            throw e // Siempre se vuelve a lanzar para no romper la cancelación
        }
    }

    /** Vuelve el progreso a 0. */
    fun reset() {
        currentProgress = 0
    }
}

/** Factor de progreso entre 0 y 1 para la barra de progreso. */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
