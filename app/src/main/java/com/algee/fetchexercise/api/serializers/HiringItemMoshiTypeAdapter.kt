package com.algee.fetchexercise.api.serializers

import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.api.model.HiringItemJson
import com.algee.fetchexercise.api.FetchApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


/**
 * Moshi type adapter to serialize(unneeded per the requirements) and to deserialize
 * the items in the json payload from the [FetchApi.getHiringItems] api.
 */
internal class HiringItemMoshiTypeAdapter {

    @ToJson
    internal fun toJson(hiringItem: HiringItem): HiringItemJson =
        if (hiringItem is HiringItemJson) {
            hiringItem
        } else {
            with(hiringItem) {
                HiringItemJson(
                    id,
                    listId,
                    name
                )
            }
        }

    @FromJson
    internal fun fromJson(hiringItemJson: HiringItemJson): HiringItem = hiringItemJson
}