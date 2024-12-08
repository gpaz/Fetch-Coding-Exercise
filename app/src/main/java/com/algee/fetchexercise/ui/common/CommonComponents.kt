package com.algee.fetchexercise.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

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
}