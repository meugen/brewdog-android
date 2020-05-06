package com.brewdog.android.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brewdog.android.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BeerDetailsFragment: Fragment() {

    private val v: BeerDetailsViewModel by viewModel {
        parametersOf(arguments.beerId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {

        private const val ARG_BEER_ID = "beer_id"

        private val Bundle?.beerId: Int
            get() = requireNotNull(this?.getInt(ARG_BEER_ID))

        fun arguments(id: Int): Bundle {
            val args = Bundle()
            args.putInt(ARG_BEER_ID, id)
            return args
        }
    }
}