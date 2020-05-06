package com.brewdog.android.ui.fragments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brewdog.android.R
import com.brewdog.android.model.entities.Beer
import com.brewdog.android.ui.utils.inflateInto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_beer.view.*

class BeersAdapter: RecyclerView.Adapter<BeersAdapter.BeerHolder>() {

    private var items: List<Beer> = emptyList()

    fun submitItems(items: List<Beer>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerHolder {
        val view = parent.inflateInto(R.layout.item_beer, false)
        return BeerHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BeerHolder, position: Int) {
        holder.bind(items[position])
    }

    class BeerHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.beerImage
        private val nameView = view.beerName
        private val firstBrewedView = view.firstBrewedDate
        private val ibuView = view.beerIbu

        fun bind(item: Beer) {
            Picasso.get().load(item.imageUrl).into(imageView)
            nameView.text = item.name
            firstBrewedView.text = item.firstBrewed
            ibuView.text = itemView.context.getString(
                R.string.item_beer_ibu_format,
                item.ibu
            )
        }
    }
}