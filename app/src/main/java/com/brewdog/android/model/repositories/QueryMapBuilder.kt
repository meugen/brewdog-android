package com.brewdog.android.model.repositories

interface QueryMapBuilder {

    fun build(): MutableMap<String, String>

    companion object EMPTY: QueryMapBuilder {

        override fun build(): MutableMap<String, String> = hashMapOf()
    }
}