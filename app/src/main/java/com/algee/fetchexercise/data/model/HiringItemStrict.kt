package com.algee.fetchexercise.data.model

import com.algee.fetchexercise.api.model.HiringItem

/**
 * A [HiringItem] implementation readily for the ui, checking first that the
 * [HiringItem.name] is not null--throwing, instead, if it is null upon creation of
 * the wrapper instance--and then providing a non-null [HiringItem.name] property
 * readily available for the ui.
 */
@JvmInline
value class HiringItemStrictWrapper(
    private val internalItem: HiringItem
) : HiringItem by internalItem {

    init {
        requireNotNull(internalItem.name)
    }

    override val name: String
        get() = internalItem.name!!

    operator fun component1(): Int = id

    operator fun component2(): Int = listId

    operator fun component3(): String = name
}
