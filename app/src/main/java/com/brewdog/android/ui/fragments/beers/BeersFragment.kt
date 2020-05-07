package com.brewdog.android.ui.fragments.beers

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brewdog.android.R
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.ui.fragments.details.BeerDetailsFragment
import kotlinx.android.synthetic.main.fragment_beers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeersFragment: Fragment(), BeersAdapter.OnBeersListener {

    private val viewModel: BeersViewModel by viewModel()
    private val adapter = BeersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_beers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(view.context)
    }

    private fun setupRecycler(context: Context) {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = this.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
        viewModel.beersData().observe(viewLifecycleOwner, Observer {
            adapter.submitItems(it)
        })
        viewModel.eventData().observe(viewLifecycleOwner, Observer {
            onEvent(it)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                findNavController().navigate(R.id.action_beers_to_filter)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onEvent(event: Any) {
        when (event) {
            is ShowProgress -> loadingGroup.visibility = View.VISIBLE
            is HideProgress -> loadingGroup.visibility = View.GONE
        }
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

    override fun onPositionDisplayed(position: Int) {
        viewModel.onPositionDisplayed(position)
    }
}