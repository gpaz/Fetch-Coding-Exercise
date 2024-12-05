package com.algee.fetchexercise.data.domain

import com.algee.fetchexercise.common.Comparators
import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.data.repository.HiringItemsRepository
import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.TreeMap

class GetItemsUseCase(
    private val hiringItemsRepository: HiringItemsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Returns the hiring json list of hiring items in a map of list of items
     * grouped by their 'listId' and sorted alphabetically by their 'name'.
     */
    suspend fun invoke(): Result<Map<Int, List<HiringItem>>, FetchApiError> =
        withContext(dispatcher) {
            hiringItemsRepository.getHiringItems().let { result ->
                if (result.isErr) {
                    Err(result.error)
                } else {
                    val map = result
                        .value
                        .filterNot { it.name.isNullOrBlank() }
                        .groupByTo(TreeMap(Comparators.IntAscendingOrder)) { it.listId }
                        .mapValues { entry ->
                            entry.value.sortedBy { it.name }
                        }
                    Ok(map)
                }
            }
        }
}
