package com.brewdog.android.model.repositories.beers

import com.brewdog.android.model.repositories.QueryMapBuilder

class FilterBuilder {

    private var filter: QueryMapBuilder = QueryMapBuilder.EMPTY

    fun addIbuCheck() {
        filter = IbuLtQueryBuilder(
            filter, 50.0
        )
    }

    fun build() = filter
}