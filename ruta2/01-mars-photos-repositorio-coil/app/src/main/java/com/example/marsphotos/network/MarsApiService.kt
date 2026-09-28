package com.example.marsphotos.network

import com.example.marsphotos.model.MarsPhoto
import retrofit2.http.GET

/**
 * Fuente de datos remota. Ya no crea Retrofit aquí: esa responsabilidad
 * pasó al contenedor de dependencias (AppContainer).
 */
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}
