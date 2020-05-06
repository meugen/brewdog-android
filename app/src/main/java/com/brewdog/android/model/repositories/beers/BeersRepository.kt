package com.brewdog.android.model.repositories.beers

import android.util.Log
import com.brewdog.android.model.api.PunkApi
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.model.repositories.QueryMapBuilder
import com.brewdog.android.model.repositories.RepositoryResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

private const val BEERS_PAGE_SIZE = 25

class BeersRepository(
    private val punkApi: PunkApi,
    private val dispatcher: CoroutineDispatcher
) {

    private val beersChannel = ConflatedBroadcastChannel<List<Beer>>()
    private var page: Int = 1
    private var filter: QueryMapBuilder = QueryMapBuilder.EMPTY

    fun beersFlow(): Flow<List<Beer>> {
        return beersChannel.asFlow()
    }

    fun beersByIdFlow(id: Int): Flow<Beer> {
        return beersChannel.asFlow()
            .transform {
                it.find { beer -> beer.id == id }
                    ?.let { beer -> emit(beer) }
            }
    }

    fun reset(newFilter: QueryMapBuilder) {
        page = 1
        filter = newFilter
        beersChannel.offer(emptyList())
    }

    suspend fun load(): RepositoryResult<List<Beer>> {
        beersChannel.valueOrNull?.let {
            return RepositoryResult.Success(it)
        }
        return withContext(dispatcher) {
            loadPage(page)
        }
    }

    private fun loadPage(page: Int): RepositoryResult<List<Beer>> {
        val pagedFilter = PageQueryBuilder(filter, page, BEERS_PAGE_SIZE)
        val call = punkApi.getBeers(pagedFilter.build())
        return try {
            val response = call.execute()
            val beers = requireNotNull(response.body())
            appendBeers(beers)
            RepositoryResult.Success(beers)
        } catch (e: Throwable) {
            Log.d("BeersRepository", e.message, e)
            RepositoryResult.Error(e)
        }
    }

    private fun appendBeers(newBeers: List<Beer>) {
        val beers = beersChannel.valueOrNull?.toMutableList()
            ?: ArrayList()
        beers.addAll(newBeers)
        beersChannel.offer(beers)
    }

    suspend fun loadNextPage(): RepositoryResult<List<Beer>> {
        page++
        return withContext(dispatcher) {
            loadPage(page)
        }
    }
}