package com.example.bookshelf.fake

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksApiService
import com.example.bookshelf.network.ImageLinks
import com.example.bookshelf.network.Volume
import com.example.bookshelf.network.VolumeInfo
import com.example.bookshelf.network.VolumeItem
import com.example.bookshelf.network.VolumesResponse
import java.io.IOException

object FakeDataSource {
    val volumes = listOf(
        Volume("a1", VolumeInfo("Libro A", listOf("Autor A"), ImageLinks(thumbnail = "http://books.google.com/a.jpg"))),
        Volume("b2", VolumeInfo("Libro B", listOf("Autor B"), ImageLinks(thumbnail = "http://books.google.com/b.jpg"))),
    )
    val books = listOf(
        Book("a1", "Libro A", listOf("Autor A"), "https://books.google.com/a.jpg"),
        Book("b2", "Libro B", listOf("Autor B"), "https://books.google.com/b.jpg"),
    )
}

class FakeBooksApiService : BooksApiService {
    override suspend fun searchVolumes(query: String, maxResults: Int) =
        VolumesResponse(FakeDataSource.volumes.size, FakeDataSource.volumes.map { VolumeItem(it.id) })

    override suspend fun getVolume(id: String): Volume = FakeDataSource.volumes.first { it.id == id }
}

class FakeBooksRepository : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> = FakeDataSource.books
}

class FakeFailingBooksRepository : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> = throw IOException("Sin red")
}
