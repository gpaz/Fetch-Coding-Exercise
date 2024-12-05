package com.algee.fetchexercise.data.repository.error

interface TypedCausedError<E : Throwable> {
    val typedCause: E
}