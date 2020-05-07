package com.brewdog.android.ui.fragments.filter

import androidx.lifecycle.ViewModel
import com.brewdog.android.model.repositories.beers.BeersRepository
import com.brewdog.android.model.repositories.beers.FilterBuilder

class FilterViewModel(
    private val beersRepository: BeersRepository
): ViewModel() {

    fun onApply(
        ibuCheck: Boolean
    ) {
        val builder = FilterBuilder()
        if (ibuCheck) {
            builder.addIbuCheck()
        }
        beersRepository.reset(builder.build())
    }
}