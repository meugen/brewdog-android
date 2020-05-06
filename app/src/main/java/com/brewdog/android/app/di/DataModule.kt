package com.brewdog.android.app.di

import com.brewdog.android.BuildConfig
import com.brewdog.android.model.api.PunkApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun dataModule() = module {

    single {
        Dispatchers.IO
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .client(buildHttpClient())
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .build()
    }

    single {
        get<Retrofit>().create(PunkApi::class.java)
    }
}

fun buildHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}

fun buildGson(): Gson {
    return GsonBuilder()
        .create()
}