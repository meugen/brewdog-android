package com.brewdog.android.ui.fragments.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brewdog.android.R
import com.brewdog.android.model.entities.beer.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_beer_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BeerDetailsFragment: Fragment() {

    private val viewModel: BeerDetailsViewModel by viewModel {
        parametersOf(arguments.beerId)
    }
    private val maltIngredientsAdapter = IngredientsAdapter()
    private val hopsIngredientsAdapter = IngredientsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beer_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHopsRecycler(view.context)
        setupMaltRecycler(view.context)
    }

    private fun setupMaltRecycler(context: Context) {
        maltIngredientsRecycler.layoutManager = LinearLayoutManager(context)
        maltIngredientsRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        maltIngredientsRecycler.adapter = maltIngredientsAdapter
    }

    private fun setupHopsRecycler(context: Context) {
        hopsIngredientsRecycler.layoutManager = LinearLayoutManager(context)
        hopsIngredientsRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        hopsIngredientsRecycler.adapter = hopsIngredientsAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        viewModel.beerData().observe(viewLifecycleOwner, Observer {
            displayBeer(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        maltIngredientsRecycler.adapter = null
        hopsIngredientsRecycler.adapter = null
    }

    private fun displayBeer(beer: Beer) {
        beerName.text = beer.name
        Picasso.get().load(beer.imageUrl).into(beerImage)
        beerDescription.text = beer.description
        maltIngredientsAdapter.submitItems(beer.ingredients.malt)
        hopsIngredientsAdapter.submitItems(beer.ingredients.hops)
        beerBrewerTips.text = beer.brewersTips
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> findNavController().popBackStack()
            else -> super.onOptionsItemSelected(item)
        }
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