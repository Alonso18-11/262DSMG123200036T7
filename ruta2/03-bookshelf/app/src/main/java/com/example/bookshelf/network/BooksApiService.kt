package com.example.bookshelf.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** https://www.googleapis.com/books/v1/ */
interface BooksApiService {
    /** Busca libros: volumes?q=jazz+history */
    @GET("volumes")
    suspend fun searchVolumes(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 30
    ): VolumesResponse

    /** Detalle de un libro: volumes/{id} (aquí vienen los imageLinks) */
    @GET("volumes/{id}")
    suspend fun getVolume(@Path("id") id: String): Volume
}
