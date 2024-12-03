package com.algee.fetchexercise.data.model.raw

import com.algee.fetchexercise.common.Constants
import com.algee.fetchexercise.data.model.HiringItem
import com.squareup.moshi.Json

data class HiringItemJson(
    override val id: Int,
    override val listId: Int,
    @Json(name = "name")
    private val nameRaw: String?
) : HiringItem {

    override val name: String
        get() = nameRaw ?: Constants.EMPTY_STR
}
