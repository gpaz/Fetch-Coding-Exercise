package com.algee.fetchexercise.common

import java.util.Comparator

class Comparators {

    object IntAscendingOrder : Comparator<Int> {
        override fun compare(left: Int, right: Int): Int = left - right
    }

    object IntDescendingOrder : Comparator<Int> {
        override fun compare(left: Int, right: Int): Int = right - left
    }
}