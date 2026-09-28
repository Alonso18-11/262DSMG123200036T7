package com.example.bookshelf.model

import com.example.bookshelf.network.Volume

/** Modelo de dominio que usa la IU (independiente del JSON de la API). */
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnailUrl: String?
)

/**
 * Convierte el volumen de la API a nuestro modelo. La API devuelve las
 * miniaturas con "http", y Android bloquea el tráfico sin cifrar,
 * así que se cambia a "https".
 */
fun Volume.toBook(): Book = Book(
    id = id,
    title = volumeInfo.title,
    authors = volumeInfo.authors,
    thumbnailUrl = (volumeInfo.imageLinks?.thumbnail ?: volumeInfo.imageLinks?.smallThumbnail)
        ?.replace("http://", "https://")
)
