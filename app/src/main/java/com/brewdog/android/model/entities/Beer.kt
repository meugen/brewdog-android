package com.brewdog.android.model.entities

import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("first_brewed")
    val firstBrewed: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("abv")
    val abv: Double,
    @SerializedName("ibu")
    val ibu: Double,
    @SerializedName("target_fg")
    val targetFg: Double,
    @SerializedName("target_og")
    val targetOg: Double,
    @SerializedName("ebc")
    val ebc: Double,
    @SerializedName("srm")
    val srm: Double,
    @SerializedName("ph")
    val ph: Double,
    @SerializedName("attenuation_level")
    val attenuationLevel: Double,
    @SerializedName("volume")
    val volume: Volume,
    @SerializedName("boil_volume")
    val boilVolume: Volume,
    @SerializedName("method")
    val methods: Methods,
    @SerializedName("ingedients")
    val ingredients: Ingredients,
    @SerializedName("food_pairing")
    val foodPairing: List<String>,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    @SerializedName("contributed_by")
    val contributedBy: String
)

data class Volume(
    @SerializedName("volume")
    val volume: Int,
    @SerializedName("unit")
    val unit: String
)

data class MethodTemp(
    @SerializedName("value")
    val value: Int,
    @SerializedName("unit")
    val unit: String
)

data class Method(
    @SerializedName("temp")
    val temp: MethodTemp,
    @SerializedName("duration")
    val duration: Int?
)

data class Methods(
    @SerializedName("mash_temp")
    val mashTemp: List<Method>,
    @SerializedName("fermentation")
    val fermentation: Method?,
    @SerializedName("twist")
    val twist: String?
)

data class IngredientAmount(
    @SerializedName("value")
    val value: Double,
    @SerializedName("unit")
    val unit: String
)

data class MaltIngredient(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: IngredientAmount
)

data class HopIngredient(
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: IngredientAmount,
    @SerializedName("add")
    val add: String,
    @SerializedName("attribute")
    val attribute: String
)

data class Ingredients(
    @SerializedName("malt")
    val malt: List<MaltIngredient>,
    @SerializedName("hops")
    val hops: List<HopIngredient>,
    @SerializedName("yeast")
    val yeast: String
)
