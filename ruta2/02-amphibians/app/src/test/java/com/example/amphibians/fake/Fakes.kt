package com.example.amphibians.fake

import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibiansApiService

object FakeDataSource {
    val amphibians = listOf(
        Amphibian("Great Basin Spadefoot", "Toad", "Descripción 1", "https://example.com/1.png"),
        Amphibian("Roraima Bush Toad", "Toad", "Descripción 2", "https://example.com/2.png"),
    )
}

class FakeAmphibiansApiService : AmphibiansApiService {
    override suspend fun getAmphibians(): List<Amphibian> = FakeDataSource.amphibians
}

class FakeAmphibiansRepository : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> = FakeDataSource.amphibians
}
