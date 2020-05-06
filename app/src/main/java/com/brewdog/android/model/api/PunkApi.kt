package com.brewdog.android.model.api

import com.brewdog.android.model.entities.beer.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PunkApi {

    @GET("beers")
    fun getBeers(
        @QueryMap params: Map<String, String>
    ): Call<List<Beer>>
}