package com.example.bookshelf.network

import kotlinx.serialization.Serializable

/*
 * Modelos de red para la API de Google Books. Solo se declaran los campos
 * que usamos; el resto del JSON se ignora (ignoreUnknownKeys = true).
 */

/** Respuesta de GET volumes?q=... */
@Serializable
data class VolumesResponse(
    val totalItems: Int = 0,
    val items: List<VolumeItem> = emptyList()
)

/** Cada resultado de búsqueda; lo que necesitamos es su id. */
@Serializable
data class VolumeItem(val id: String)

/** Respuesta de GET volumes/{id} */
@Serializable
data class Volume(
    val id: String,
    val volumeInfo: VolumeInfo = VolumeInfo()
)

@Serializable
data class VolumeInfo(
    val title: String = "",
    val authors: List<String> = emptyList(),
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)
