package com.brewdog.android.model.repositories.beers

import com.brewdog.android.model.repositories.QueryMapBuilder

private const val PARAM_PAGE = "page"
private const val PARAM_PER_PAGE = "per_page"
private const val PARAM_IBU_LT = "ibu_lt"
private const val PARAM_IBU_GT = "ibu_gt"

class PageQueryBuilder(
    private val baseBuilder: QueryMapBuilder,
    private val page: Int,
    private val perPage: Int
): QueryMapBuilder {

    override fun build(): MutableMap<String, String> {
        val result = baseBuilder.build()
        result.put(PARAM_PAGE, page.toString())
        result.put(PARAM_PER_PAGE, perPage.toString())
        return result;
    }
}

class IbuLtQueryBuilder(
    private val baseBuilder: QueryMapBuilder,
    private val ibuLtValue: Double
): QueryMapBuilder {

    override fun build(): MutableMap<String, String> {
        val result = baseBuilder.build()
        result.put(PARAM_IBU_LT, ibuLtValue.toString())
        return result
    }
}

 class IbuGtQueryBuilder(
     private val baseBuilder: QueryMapBuilder,
     private val ibuGtValue: Double
 ): QueryMapBuilder {

     override fun build(): MutableMap<String, String> {
         val result = baseBuilder.build()
         result.put(PARAM_IBU_GT, ibuGtValue.toString())
         return result
     }
 }