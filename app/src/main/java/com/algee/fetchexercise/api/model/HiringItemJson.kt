package com.algee.fetchexercise.api.model

internal data class HiringItemJson(
    override val id: Int,
    override val listId: Int,
    override val name: String?
) : HiringItem