package com.algee.fetchexercise.data.model

import com.algee.fetchexercise.api.model.HiringItem

@JvmInline
value class HiringItemStrictWrapper(
    private val internalItem: HiringItem
) : HiringItem by internalItem {

    init {
        requireNotNull(internalItem.name)
    }

    override val name: String
        get() = internalItem.name!!
}
