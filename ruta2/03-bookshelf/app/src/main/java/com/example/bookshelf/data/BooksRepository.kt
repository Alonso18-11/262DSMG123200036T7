package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.toBook
import com.example.bookshelf.network.BooksApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {

    /**
     * 1) Busca los ids de los libros que coinciden con la consulta.
     * 2) Pide el detalle de cada libro EN PARALELO con async/awaitAll
     *    (descomposición paralela vista en la ruta 1).
     * Si falla el detalle de un libro concreto, se omite sin tumbar toda la lista.
     */
    override suspend fun getBooks(query: String): List<Book> = coroutineScope {
        val ids = booksApiService.searchVolumes(query).items.map { it.id }.distinct()
        ids.map { id ->
            async {
                try {
                    booksApiService.getVolume(id).toBook()
                } catch (e: IOException) {
                    null
                } catch (e: HttpException) {
                    null
                }
            }
        }.awaitAll().filterNotNull()
    }
}
