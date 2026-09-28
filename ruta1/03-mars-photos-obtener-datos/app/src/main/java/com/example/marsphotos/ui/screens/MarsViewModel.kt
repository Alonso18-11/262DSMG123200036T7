package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/** Estados posibles de la pantalla principal. */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    data object Error : MarsUiState
    data object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    /** El estado mutable guarda el resultado de la solicitud más reciente. */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    /**
     * Obtiene las fotos desde el servicio con Retrofit. Se usa viewModelScope:
     * la corrutina se cancela sola cuando el ViewModel se destruye.
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val listResult = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success("Éxito: se obtuvieron ${listResult.size} fotos de Marte")
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}
