package com.brewdog.android.ui.fragments.beers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.model.repositories.beers.BeersRepository
import com.brewdog.android.ui.utils.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val LOAD_MORE_THRESHOLD = 5

class BeersViewModel(
    private val beersRepository: BeersRepository
): ViewModel() {

    private val eventData = SingleLiveEvent<Any>()
    private val beersData = MutableLiveData<List<Beer>>()
    private val beersCount: Int
        get() = beersData.value?.size ?: 0

    private var lastLoadedPosition = 0

    init {
        viewModelScope.launch {
            beersRepository.beersFlow().collect {
                beersData.value = it
                lastLoadedPosition = 0
            }
        }
    }

    fun onStart() {
        viewModelScope.launch {
            eventData.setValue(ShowProgress())
            beersRepository.load()
            eventData.setValue(HideProgress())
        }
    }

    fun eventData(): LiveData<Any> = eventData

    fun beersData(): LiveData<List<Beer>> = beersData

    fun onPositionDisplayed(position: Int) {
        if (lastLoadedPosition < beersCount && position >= beersCount - LOAD_MORE_THRESHOLD) {
            lastLoadedPosition = beersCount
            onLoadMore()
        }
    }

    private fun onLoadMore() {
        viewModelScope.launch {
            eventData.setValue(ShowProgress())
            beersRepository.loadNextPage()
            eventData.setValue(HideProgress())
        }
    }
}

class ShowProgress
class HideProgress