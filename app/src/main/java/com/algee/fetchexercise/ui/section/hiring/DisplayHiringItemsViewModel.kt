package com.algee.fetchexercise.ui.section.hiring

import androidx.lifecycle.viewModelScope
import com.algee.fetchexercise.data.domain.GetItemsUseCase
import com.algee.fetchexercise.data.model.HiringItemStrictWrapper
import com.algee.fetchexercise.data.repository.error.FetchApiError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeViewModel
import retrofit2.HttpException
import java.io.IOException

class DisplayHiringItemsViewModel(
    private val getItemsUseCase: GetItemsUseCase,
) : ScopeViewModel() {

    private val mHiringItemsFlow: MutableStateFlow<HiringItemsState> =
        MutableStateFlow(HiringItemsState.Uninitialized)

    private val mErrorFlow: MutableSharedFlow<ErrorState<Throwable>> =
        MutableSharedFlow()

    private val mIsProcessing: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val hiringItems: StateFlow<HiringItemsState> =
        mHiringItemsFlow.asStateFlow()

    val error: SharedFlow<ErrorState<Throwable>> =
        mErrorFlow.asSharedFlow()

    val isProcessing: StateFlow<Boolean> =
        mIsProcessing.asStateFlow()

    sealed class HiringItemsState {

        data object Uninitialized: HiringItemsState()

        data class Updated(
            val value: Map<Int, List<HiringItemStrictWrapper>>
        ): HiringItemsState()
    }

    sealed interface ErrorState<out T : Throwable> {

        val value: T?

        val message: String?
            get() = value?.localizedMessage

        data object NoError : ErrorState<Nothing> {
            override val value: Nothing?
                get() = null
        }

        @JvmInline
        value class NoInternet(
            override val value: IOException
        ) : ErrorState<IOException>

        @JvmInline
        value class HttpError(
            override val value: HttpException
        ) : ErrorState<HttpException> {
            val code: Int
                get() = value.code()
        }

        @JvmInline
        value class UnclassifiedError(
            override val value: Throwable
        ) : ErrorState<Throwable> {

            val type: String
                get() = value::class.simpleName ?: "<null>"

        }
    }

    fun requestHiringItems(): Boolean {
        if (isProcessing.value /*is true*/) {
            return false
        } else {
            mIsProcessing.value = true
            viewModelScope.launch {
                delay(5000L)
                getItemsUseCase().let { useCaseResult ->
                    if (useCaseResult.isErr) {
                        val useCaseError = useCaseResult.error
                        when (useCaseError) {
                            is FetchApiError.NoInternet -> {
                                ErrorState.NoInternet(useCaseError.typedCause)
                            }
                            is FetchApiError.Http -> {
                                ErrorState.HttpError(useCaseError.typedCause)
                            }
                            is FetchApiError.Unclassified -> {
                                ErrorState.UnclassifiedError(useCaseError.typedCause)
                            }
                        }.also {
                            mErrorFlow.emit(it)
                        }
                    } else {
                        // Reset error
                        mErrorFlow.emit(ErrorState.NoError)
                        // Update results
                        useCaseResult.value.let {
                            mHiringItemsFlow.emit(
                                HiringItemsState.Updated(
                                    value = it
                                )
                            )
                        }
                        useCaseResult.value
                    }
                }
                mIsProcessing.emit(false)
            }
            return true
        }
    }
}
