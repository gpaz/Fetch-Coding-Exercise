package com.algee.fetchexercise.api.serializers

import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.api.model.HiringItemJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

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