package com.algee.fetchexercise.ui.common

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

/**
 * A Container for common compose components.  This can be
 * put here and moved to a separate module later on if necessary,
 * which, for the purpose of the exercise, is not needed.
 */
object CommonComponents {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FetchExerciseSimpleTopAppBar(
        title: String
    ) {
        TopAppBar(
            title = {
                Text(text = title)
            }
        )
    }

    fun Activity.finishActivityCallback(resultCode: Int = RESULT_OK, intent: Intent? = null): CallbackOutUnitOnly = {
        setResult(resultCode, intent)
        finish()
    }
}

typealias CallbackOutUnitOnly = () -> Unit