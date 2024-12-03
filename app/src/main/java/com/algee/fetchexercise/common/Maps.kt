package com.algee.fetchexercise.common

object Maps {

    /**
     * Note: Taken from the kotlin std library 'MapsJVM.kt' file.
     *
     * Calculate the initial capacity of a map, based on Guava's
     * com.google.common.collect.Maps.capacity approach.
     */
    fun mapCapacity(expectedSize: Int): Int = when {
        // We are not coercing the value to a valid one and not throwing an exception. It is up to the caller to
        // properly handle negative values.
        expectedSize < 0 -> expectedSize
        expectedSize < 3 -> expectedSize + 1
        expectedSize < INT_MAX_POWER_OF_TWO -> ((expectedSize / 0.75F) + 1.0F).toInt()
        // any large value
        else -> Int.MAX_VALUE
    }
    private const val INT_MAX_POWER_OF_TWO: Int = 1 shl (Int.SIZE_BITS - 2)
}