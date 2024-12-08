package com.algee.fetchexercise.ui.section.hiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algee.fetchexercise.R
import com.algee.fetchexercise.data.model.HiringItemStrictWrapper
import com.algee.fetchexercise.ui.common.CommonComponents
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.ErrorState.NoError
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.HiringItemsState.Uninitialized
import com.algee.fetchexercise.ui.section.hiring.DisplayHiringItemsViewModel.HiringItemsState.Updated
import com.algee.fetchexercise.ui.theme.FetchExerciseTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DisplayHiringItemsActivity : ComponentActivity() {

    /*
    private val viewModel: DisplayHiringItemsViewModel by viewModel<DisplayHiringItemsViewModel>(
        qualifier = qualifier(
            enum = DisplayHiringItemsModule.Qualifiers.HiringItemMap
        )
    )
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        Scaffold(
            topBar = {
                CommonComponents.FetchExerciseSimpleTopAppBar("sdf")
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                val hiringItemsState by theViewModel.hiringItems.collectAsState()
                when (hiringItemsState) {
                    Uninitialized -> {
                        TODO("show a generic background")
                    }

                    is Updated -> {
                        // Show list of items grouped by id
                        LazyColumn(
                            contentPadding = it,
                        ) {
                            val groupedHiringItemEntries = (hiringItemsState as Updated)
                                .value
                                .entries
                                .toList()
                            items(groupedHiringItemEntries) { (listId, hiringItems) ->
                                HiringItemsGroup(listId, hiringItems)
                            }
                        }
                    }
                }

                val isProcessing by theViewModel.isProcessing.collectAsState()
                val errorState by theViewModel.error.collectAsState(NoError)
                if (isProcessing) {
                    TODO("Show progress dialog")
                } else {
                    // Show any errors that occurred
                    when (errorState) {
                        NoError -> {
                            // Do nothing
                        }
                        is DisplayHiringItemsViewModel.ErrorState.NoInternet -> TODO("Show NO-INTERNET message")
                        is DisplayHiringItemsViewModel.ErrorState.HttpError -> TODO("Show HTTP-ERROR message")
                        is DisplayHiringItemsViewModel.ErrorState.UnclassifiedError -> TODO("Show UNCLASSIFIED-ERROR")
                    }}

            }
        }

    }

    @Composable
    private fun HiringItemsGroup(
        listId: Int,
        hiringItems: List<HiringItemStrictWrapper>
    ) {
        Text(

            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            //.align(alignment = Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "List ID: $listId",
            fontSize = 25.sp,
        )
        LazyColumn {
            items(hiringItems) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .defaultMinSize(minHeight = 100.dp),
                ) {
                    ItemName(it.name)
                    ItemId(it.id)
                }
            }
        }
    }

    @Composable
    private fun ItemName(name: String) = KeyAndValue(
        key = getString(R.string.hiring_item_property_title_name),
        value = name
    )

    @Composable
    private fun ItemId(id: Int) = KeyAndValue(
        key = getString(R.string.hiring_item_property_title_id),
        value = id.toString()
    )

    @Composable
    private fun KeyAndValue(key: String, value: String) {
        Text(
            modifier = Modifier,
            //.align(alignment = Alignment.Start),
            textAlign = TextAlign.Start,
            text = "$key: $value"
        )
    }
}