package com.brewdog.android.app.di

import com.brewdog.android.model.api.PunkApi
import com.brewdog.android.model.repositories.beers.BeersRepository
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.dsl.module

fun repositoriesModule() = module {

    single {
        BeersRepository(
            get<PunkApi>(),
            get<CoroutineDispatcher>()
        )
    }
}