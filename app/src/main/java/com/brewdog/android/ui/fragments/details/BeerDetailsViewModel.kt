package com.brewdog.android.ui.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.model.repositories.beers.BeersRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeerDetailsViewModel(
    private val beersRepository: BeersRepository,
    private val beerId: Int
): ViewModel() {

    private val beerData = MutableLiveData<Beer>()

    init {
        viewModelScope.launch {
            beersRepository.beersByIdFlow(beerId).collect {
                beerData.value = it
            }
        }
    }

    fun beerData(): LiveData<Beer> = beerData
}