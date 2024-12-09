package com.algee.fetchexercise.ui.section.hiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algee.fetchexercise.R
import com.algee.fetchexercise.ui.common.CallbackOutUnitOnly
import com.algee.fetchexercise.ui.common.CommonComponents
import com.algee.fetchexercise.ui.common.CommonComponents.finishActivityCallback
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.ErrorState.HttpError
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.ErrorState.NoError
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.ErrorState.NoInternet
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.ErrorState.UnclassifiedError
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.HiringItemsState.Uninitialized
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.HiringItemsState.Updated
import com.algee.fetchexercise.ui.theme.FetchExerciseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Displays the hiring items found from the Fetch
 * 'hiring.json' api for this coding exercise.
 */
class DisplayHiringItemsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchExerciseTheme {
                val viewModel = getViewModel<DisplayHiringItemsViewModel>()
                DisplayHiringItemsScreen(viewModel)
            }
        }
    }

    @Composable
    private fun DisplayHiringItemsScreen(
        theViewModel: DisplayHiringItemsViewModel
    ) {
        val scope = rememberCoroutineScope()
        val runOnce = rememberSaveable { true }
        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(runOnce) {
            theViewModel.requestHiringItems()
        }
        Scaffold(
            modifier = Modifier.padding(20.dp),
            topBar = {
                CommonComponents.FetchExerciseSimpleTopAppBar(
                    getString(R.string.activity_display_hiring_items_name)
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            val isProcessing by theViewModel.isProcessing.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                val hiringItemsState by theViewModel.hiringItems.collectAsState()
                when (hiringItemsState) {
                    Uninitialized -> {
                        Text(
                            modifier = Modifier.wrapContentSize()
                                .padding(paddingValues)
                                .align(Alignment.Center),
                            text = getString(R.string.blank_screen_text),
                            textAlign = TextAlign.Center,
                            fontSize = 36.sp
                        )
                    }

                    is Updated -> {
                        // Show list of items grouped by id
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = 0.dp,
                                bottom = 10.dp,
                                start = 5.dp,
                                end = 5.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val groupedHiringItemEntries = (hiringItemsState as Updated)
                                .value
                                .entries
                                .toList()
                            groupedHiringItemEntries.forEach { (listId, hiringItems) ->
                                item {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        //.align(alignment = Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center,
                                        text = "List ID: $listId",
                                        fontSize = 26.sp,
                                    )
                                }
                                items(hiringItems) { (id, _, name) ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        elevation = CardDefaults
                                            .cardElevation(defaultElevation = 4.dp),
                                    ) {
                                        val padding = PaddingValues(
                                            horizontal = 20.dp,
                                        )
                                        ItemName(
                                            modifier = Modifier.padding(padding),
                                            name = name
                                        )
                                        ItemId(
                                            modifier = Modifier.padding(padding),
                                            id = id
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                val errorState by theViewModel.error.collectAsState(NoError)
                if (isProcessing) {
                    CircularProgressIndicator()
                } else {
                    // Show any errors that occurred
                    when (errorState) {
                        NoError -> {
                            // Do nothing
                        }
                        is NoInternet -> {
                            val message = getString(R.string.alert_no_internet)
                            ShowFailedRetrievalSnackbar(
                                viewModel = theViewModel,
                                coroutineScope = scope,
                                snackbarHostState = snackbarHostState,
                                message = message,
                            )
                        }
                        is HttpError -> {
                            val httpErrorState: HttpError = errorState as HttpError
                            val message = getString(
                                R.string.alert_http_error_fmt,
                                httpErrorState.code,
                                httpErrorState.message
                            )
                            ShowFailedRetrievalSnackbar(
                                viewModel = theViewModel,
                                coroutineScope = scope,
                                snackbarHostState = snackbarHostState,
                                message = message,
                            )
                        }
                        is UnclassifiedError -> {
                            val unclassifiedErrorState = errorState as UnclassifiedError
                            val message = getString(
                                R.string.alert_unclassified_error_fmt,
                                unclassifiedErrorState.type,
                                unclassifiedErrorState.message
                            )
                            ShowFailedRetrievalSnackbar(
                                viewModel = theViewModel,
                                coroutineScope = scope,
                                snackbarHostState = snackbarHostState,
                                message = message,
                            )
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun ItemName(
        modifier: Modifier = Modifier,
        name: String
    ) = KeyAndValue(
        modifier = modifier,
        key = getString(R.string.hiring_item_property_title_name),
        value = name
    )

    @Composable
    private fun ItemId(
        modifier: Modifier = Modifier,
        id: Int
    ) = KeyAndValue(
        modifier = modifier,
        key = getString(R.string.hiring_item_property_title_id),
        value = id.toString()
    )

    @Composable
    private fun KeyAndValue(
        modifier: Modifier = Modifier,
        key: String,
        value: String
    ) {
        Text(
            modifier = modifier,
            textAlign = TextAlign.Start,
            text = "$key: $value"
        )
    }

    @Composable
    private fun ShowFailedRetrievalSnackbar(
        viewModel: DisplayHiringItemsViewModel,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        message: String,
        onDismiss: CallbackOutUnitOnly = finishActivityCallback(resultCode = RESULT_CANCELED),
        onRetry: CallbackOutUnitOnly = { viewModel.requestHiringItems() }
    ) {
        LaunchedEffect(snackbarHostState) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = getString(R.string.try_again)
                )
                when (result) {
                    SnackbarResult.Dismissed -> {
                        onDismiss()
                    }
                    SnackbarResult.ActionPerformed -> {
                        onRetry()
                    }
                }
            }
        }
    }
}