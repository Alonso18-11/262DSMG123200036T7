package com.example.marsphotos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Una foto de Marte tal como llega en el JSON:
 * { "id": "424906", "img_src": "https://mars.jpl.nasa.gov/..." }
 */
@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
