package com.brewdog.android.ui.fragments.beers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brewdog.android.R
import com.brewdog.android.model.entities.beer.Beer
import com.brewdog.android.ui.utils.inflateInto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_beer.view.*

class BeersAdapter(
    private val listener: OnBeersListener
): RecyclerView.Adapter<BeersAdapter.BeerHolder>() {

    private var items: List<Beer> = emptyList()

    fun submitItems(items: List<Beer>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerHolder {
        val view = parent.inflateInto(R.layout.item_beer, false)
        return BeerHolder(
            view,
            listener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BeerHolder, position: Int) {
        listener.onPositionDisplayed(position)
        holder.bind(items[position])
    }

    class BeerHolder(view: View, private val listener: OnBeersListener): RecyclerView.ViewHolder(view) {

        private val imageView = view.beerImage
        private val nameView = view.beerName
        private val firstBrewedView = view.firstBrewedDate
        private val ibuView = view.beerIbu

        private var beer: Beer? = null

        init {
            view.container.setOnClickListener {
                beer?.let { beer -> listener.onBeerClick(beer) }
            }
        }

        fun bind(item: Beer) {
            this.beer = item

            Picasso.get().load(item.imageUrl).into(imageView)
            nameView.text = item.name
            firstBrewedView.text = item.firstBrewed
            ibuView.text = itemView.context.getString(
                R.string.item_beer_ibu_format,
                item.ibu
            )
        }
    }

    interface OnBeersListener {

        fun onBeerClick(beer: Beer)
        fun onPositionDisplayed(position: Int)
    }
}