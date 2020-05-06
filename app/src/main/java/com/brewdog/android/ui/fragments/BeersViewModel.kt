package com.brewdog.android.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brewdog.android.model.entities.Beer
import com.brewdog.android.model.repositories.QueryMapBuilder
import com.brewdog.android.model.repositories.beers.BeersRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeersViewModel(
    private val beersRepository: BeersRepository
): ViewModel() {

    private val beersData = MutableLiveData<List<Beer>>()

    init {
        viewModelScope.launch {
            beersRepository.beersFlow().collect {
                beersData.value = it
            }
        }
        viewModelScope.launch {
            beersRepository.load(QueryMapBuilder.EMPTY)
        }
    }

    fun beersData(): LiveData<List<Beer>> = beersData
}