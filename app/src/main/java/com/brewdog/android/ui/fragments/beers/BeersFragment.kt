package com.brewdog.android.ui.fragments.beers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brewdog.android.R
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.ui.fragments.details.BeerDetailsFragment
import kotlinx.android.synthetic.main.fragment_beers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeersFragment: Fragment(), BeersAdapter.OnBeersListener {

    private val viewModel: BeersViewModel by viewModel()
    private val adapter =
        BeersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(requireContext())
    }

    private fun setupRecycler(context: Context) {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = this.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.beersData().observe(viewLifecycleOwner, Observer {
            adapter.submitItems(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler.adapter = null
    }

    override fun onBeerClick(beer: Beer) {
        val controller = requireView().findNavController()
        controller.navigate(
            R.id.action_beers_to_details,
            BeerDetailsFragment.arguments(beer.id)
        )
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }
}