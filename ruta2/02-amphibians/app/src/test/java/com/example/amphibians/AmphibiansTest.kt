package com.example.amphibians

import com.example.amphibians.data.NetworkAmphibiansRepository
import com.example.amphibians.fake.FakeAmphibiansApiService
import com.example.amphibians.fake.FakeAmphibiansRepository
import com.example.amphibians.fake.FakeDataSource
import com.example.amphibians.rules.TestDispatcherRule
import com.example.amphibians.ui.screens.AmphibiansUiState
import com.example.amphibians.ui.screens.AmphibiansViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AmphibiansTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun networkRepository_getAmphibians_verifyList() = runTest {
        val repository = NetworkAmphibiansRepository(FakeAmphibiansApiService())
        assertEquals(FakeDataSource.amphibians, repository.getAmphibians())
    }

    @Test
    fun viewModel_getAmphibians_verifyUiStateSuccess() = runTest {
        val viewModel = AmphibiansViewModel(FakeAmphibiansRepository())
        assertEquals(AmphibiansUiState.Success(FakeDataSource.amphibians), viewModel.amphibiansUiState)
    }
}
