package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.fake.FakeBooksApiService
import com.example.bookshelf.fake.FakeBooksRepository
import com.example.bookshelf.fake.FakeDataSource
import com.example.bookshelf.fake.FakeFailingBooksRepository
import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.screens.BookshelfUiState
import com.example.bookshelf.ui.screens.BookshelfViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookshelfTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun networkRepository_getBooks_convertsToHttpsBooks() = runTest {
        val repository = NetworkBooksRepository(FakeBooksApiService())
        assertEquals(FakeDataSource.books, repository.getBooks("jazz"))
    }

    @Test
    fun viewModel_init_uiStateSuccess() = runTest {
        val viewModel = BookshelfViewModel(FakeBooksRepository())
        assertEquals(BookshelfUiState.Success(FakeDataSource.books), viewModel.uiState)
    }

    @Test
    fun viewModel_networkError_uiStateError() = runTest {
        val viewModel = BookshelfViewModel(FakeFailingBooksRepository())
        assertEquals(BookshelfUiState.Error, viewModel.uiState)
    }
}
