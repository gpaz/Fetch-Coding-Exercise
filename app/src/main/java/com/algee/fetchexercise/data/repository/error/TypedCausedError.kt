package com.algee.fetchexercise.data.repository.error

interface TypedCausedError<E : Exception> {
    val typedCause: E
}