package com.algee.fetchexercise.data.repository.error

/**
 * When we know the exact type of error a class will be holding on to or referencing.
 */
interface TypedCausedError<E : Throwable> {
    val typedCause: E
}