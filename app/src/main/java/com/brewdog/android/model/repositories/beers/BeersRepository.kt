package com.brewdog.android.model.repositories.beers

import android.util.Log
import com.brewdog.android.model.api.PunkApi
import com.brewdog.android.model.entities.Beer
import com.brewdog.android.model.repositories.QueryMapBuilder
import com.brewdog.android.model.repositories.RepositoryResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BeersRepository(
    private val punkApi: PunkApi,
    private val dispatcher: CoroutineDispatcher
) {

    private val beersChannel = ConflatedBroadcastChannel<List<Beer>>()

    fun beersFlow(): Flow<List<Beer>> {
        return beersChannel.asFlow()
    }

    fun beersByIdFlow(id: Int): Flow<Beer?> {
        return beersChannel.asFlow()
            .map { it.find { beer -> beer.id == id } }
    }

    fun reset() {
        beersChannel.offer(emptyList())
    }

    suspend fun load(builder: QueryMapBuilder): RepositoryResult<List<Beer>> {
        return withContext(dispatcher) {
            val call = punkApi.getBeers(builder.build())
            try {
                val response = call.execute()
                val beers = requireNotNull(response.body())
                appendBeers(beers)
                RepositoryResult.Success(beers)
            } catch (e: Throwable) {
                Log.d("BeersRepository", e.message, e)
                RepositoryResult.Error<List<Beer>>(e)
            }
        }
    }

    private fun appendBeers(newBeers: List<Beer>) {
        val beers = beersChannel.valueOrNull?.toMutableList()
            ?: ArrayList()
        beers.addAll(newBeers)
        beersChannel.offer(beers)
    }
}