package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    data object Error : BookshelfUiState
    data object Loading : BookshelfUiState
}

const val DEFAULT_QUERY = "jazz history"

class BookshelfViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    var uiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    var query: String by mutableStateOf(DEFAULT_QUERY)
        private set

    private var searchJob: Job? = null

    init {
        searchBooks()
    }

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun searchBooks() {
        val q = query.trim()
        if (q.isEmpty()) return
        // Si el usuario busca otra vez, se cancela la búsqueda anterior
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            uiState = BookshelfUiState.Loading
            uiState = try {
                BookshelfUiState.Success(booksRepository.getBooks(q))
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                BookshelfViewModel(application.container.booksRepository)
            }
        }
    }
}
