package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

/**
 * Application personalizada: crea el contenedor de dependencias una sola vez
 * para que todas las clases de la app lo compartan.
 */
class MarsPhotosApplication : Application() {
    /** Instancia de AppContainer usada por el resto de las clases. */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
