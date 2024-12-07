package com.algee.fetchexercise.ui.section.hiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.algee.fetchexercise.ui.theme.FetchExerciseTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DisplayHiringItemsActivity : ComponentActivity() {

    private val viewModel: DisplayHiringItemsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchExerciseTheme {
               DisplayHiringItemsScreen()
            }
        }
    }

    @Composable
    fun DisplayHiringItemsScreen(
        viewModel: DisplayHiringItemsViewModel = koinViewModel()
    ) {
        viewModel.something
    }
}