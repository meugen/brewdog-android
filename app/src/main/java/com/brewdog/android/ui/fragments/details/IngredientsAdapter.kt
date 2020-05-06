package com.brewdog.android.ui.fragments.details

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brewdog.android.R
import com.brewdog.android.model.entities.beer.HopIngredient
import com.brewdog.android.model.entities.beer.MaltIngredient
import com.brewdog.android.ui.utils.inflateInto
import kotlinx.android.synthetic.main.item_beer_hops_ingredient.view.*
import kotlinx.android.synthetic.main.item_beer_malt_ingredient.view.*

private const val VIEW_TYPE_MALT_INGREDIENT = 1
private const val VIEW_TYPE_HOPS_INGREDIENT = 2

class IngredientsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = emptyList<Any>()

    fun submitItems(newItems: List<Any>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item) {
            is MaltIngredient -> VIEW_TYPE_MALT_INGREDIENT
            is HopIngredient -> VIEW_TYPE_HOPS_INGREDIENT
            else -> throw IllegalArgumentException("Unknown ingredient type ${item::javaClass}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HOPS_INGREDIENT -> parent.createHopIngredientHolder()
            VIEW_TYPE_MALT_INGREDIENT -> parent.createMaltIngredientHolder()
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MaltIngredientHolder -> holder.bind(items[position] as MaltIngredient)
            is HopIngredientHolder -> holder.bind(items[position] as HopIngredient)
        }
    }

    class MaltIngredientHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ingredientNameView = view.maltIngredientName
        private val ingredientAmountView = view.maltIngredientAmount
        private val ingredientUnitView = view.maltIngredientUnit

        fun bind(ingredient: MaltIngredient) {
            ingredientNameView.text = ingredient.name
            ingredientAmountView.text = String.format("%.1f", ingredient.amount.value)
            ingredientUnitView.text = ingredient.amount.unit
        }
    }

    class HopIngredientHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ingredientNameView = view.hopsIngredientName
        private val ingredientAmountView = view.hopsIngredientAmount
        private val ingredientUnitView = view.hopsIngredientUnit

        fun bind(ingredient: HopIngredient) {
            ingredientNameView.text = ingredient.name
            ingredientAmountView.text = String.format("%.1f", ingredient.amount.value)
            ingredientUnitView.text = ingredient.amount.unit
        }
    }
}

fun ViewGroup.createMaltIngredientHolder(): IngredientsAdapter.MaltIngredientHolder {
    val view = inflateInto(R.layout.item_beer_malt_ingredient, false)
    return IngredientsAdapter.MaltIngredientHolder(view)
}

fun ViewGroup.createHopIngredientHolder(): IngredientsAdapter.HopIngredientHolder {
    val view = inflateInto(R.layout.item_beer_hops_ingredient, false)
    return IngredientsAdapter.HopIngredientHolder(view)
}