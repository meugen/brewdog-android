package com.brewdog.android.app.di

import com.brewdog.android.model.repositories.beers.BeersRepository
import com.brewdog.android.ui.fragments.BeersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelsModule() = module {

    viewModel {
        BeersViewModel(
            get<BeersRepository>()
        )
    }
}