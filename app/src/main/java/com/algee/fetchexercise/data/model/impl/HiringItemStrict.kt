package com.algee.fetchexercise.data.model.impl

import com.algee.fetchexercise.data.model.HiringItem

data class HiringItemStrict(
    override val id: Int,
    override val listId: Int,
    override val name: String
) : HiringItem {

    init {
        // It should always be a valid name, meaning it can't be blank.
        if (name.isBlank()) {
            throw IllegalArgumentException("Name is blank (id: $id)")
        }
    }

    override fun hasAValidName(): Boolean = true
}