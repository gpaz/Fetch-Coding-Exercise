package com.algee.fetchexercise.common

import java.util.Comparator

/**
 * Container for common comparators used for sorting collections.
 */
object Comparators {

    /**
     * Simple integer comparator for sorting int values
     * in ascending order (from smallest to largest).
     */
    object IntAscendingOrder : Comparator<Int> {
        override fun compare(left: Int, right: Int): Int = left - right
    }

    /*  Note: just had to make this as well, commenting out for reference only.
    object IntDescendingOrder : Comparator<Int> {
        override fun compare(left: Int, right: Int): Int = right - left
    }
    */
}