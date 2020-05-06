package com.brewdog.android.model.repositories.beers

import com.brewdog.android.model.repositories.QueryMapBuilder

private const val PARAM_PAGE = "page"
private const val PARAM_PER_PAGE = "per_page"

class PageQueryBuilder(
    private val baseBuilder: QueryMapBuilder,
    private val page: Int,
    private val perPage: Int
): QueryMapBuilder {

    constructor(page: Int, perPage: Int): this(QueryMapBuilder.EMPTY, page, perPage)

    override fun build(): MutableMap<String, String> {
        val result = baseBuilder.build()
        result.put(PARAM_PAGE, page.toString())
        result.put(PARAM_PER_PAGE, perPage.toString())
        return result;
    }
}