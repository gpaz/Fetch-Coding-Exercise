package com.algee.fetchexercise.api.model

import com.algee.fetchexercise.api.FetchApi

/**
 * [HiringItem] derived from the [FetchApi.getHiringItems] api endpoint, deserializing the json
 * exactly how it is seen from the received payload.
 */
internal data class HiringItemJson(
    override val id: Int,
    override val listId: Int,
    override val name: String?
) : HiringItem