package com.algee.fetchexercise.data.domain

import com.algee.fetchexercise.api.model.HiringItem
import com.algee.fetchexercise.data.repository.HiringItemsRepository
import com.algee.fetchexercise.data.repository.HiringItemsRepositoryTest
import com.algee.fetchexercise.data.repository.error.FetchApiError
import com.algee.fetchexercise.data.repository.error.FetchApiError.NoInternet
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class GetItemsUseCaseTest {

    @MockK
    lateinit var mMockHiringItemsRepository: HiringItemsRepository

    @MockK
    lateinit var mMockHttpException: HttpException

    private val mTestCoroutineScheduler: TestCoroutineScheduler = TestCoroutineScheduler()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invokeSuccess() {
        coEvery { mMockHiringItemsRepository.getHiringItems() } returns Ok(HiringItemsRepositoryTest.SAMPLE_HIRING_ITEMS)
        val dispatcher = StandardTestDispatcher(scheduler = TestCoroutineScheduler())
        val useCase = GetItemsUseCase(mMockHiringItemsRepository, dispatcher)
        runTest(dispatcher) {
            val result = useCase.invoke()
            dispatcher.scheduler.advanceUntilIdle()
            assertTrue(result.isOk)
            result.value.let {
                // verify contents are as expected, only groups 2 and 3 should exist
                assertEquals(2, it.size)
                // verify that they are in he correct order (ascending
                // key first, then items in ascending name order)
                it.entries.forEachIndexed { index, item ->
                    when (index) {
                        0 -> {
                            assertEquals(2, item.key)
                            assertEquals(1, item.value.size)
                            assertEquals("Item 2", item.value[0].name)
                        }
                        1 -> {
                            assertEquals(3, item.key)
                            assertEquals(3, item.value.size)
                            assertEquals("Item 5", item.value[0].name)
                            assertEquals("Item 6", item.value[1].name)
                            assertEquals("Item 9", item.value[2].name)
                        }
                        else -> assert(false) { "item(%item) at index $index should not exist" }
                    }
                }
            }
        }
    }

    @Test
    fun invokeNoInternetError() {
        coEvery {
            mMockHiringItemsRepository.getHiringItems()
        } returns Err(NoInternet(IOException("Test")))

        val dispatcher = StandardTestDispatcher(scheduler = mTestCoroutineScheduler)
        val useCase = GetItemsUseCase(mMockHiringItemsRepository, dispatcher)

        runTest(dispatcher) {
            val result = useCase.invoke()
            dispatcher.scheduler.advanceUntilIdle()
            assertTrue(result.isErr)
            assertTrue(result.error is NoInternet)
        }
    }

    @Test
    fun invokeHttpError() {
        val errorResult: Result<List<HiringItem>, FetchApiError> =
            Err(FetchApiError.Http(mMockHttpException))
        coEvery {
            mMockHiringItemsRepository.getHiringItems()
        } returns errorResult

        val dispatcher = StandardTestDispatcher(scheduler = mTestCoroutineScheduler)
        val useCase = GetItemsUseCase(mMockHiringItemsRepository, dispatcher)

        runTest(dispatcher) {
            val result = useCase.invoke()
            dispatcher.scheduler.advanceUntilIdle()
            assertTrue(result.isErr)
            assertTrue(result.error is FetchApiError.Http)
        }
    }

    @Test
    fun invokeUnclassifiedError() {
        val errorResult: Result<List<HiringItem>, FetchApiError> =
            Err(FetchApiError.Unclassified(Throwable("Test")))
        coEvery {
            mMockHiringItemsRepository.getHiringItems()
        } returns errorResult

        val dispatcher = StandardTestDispatcher(scheduler = mTestCoroutineScheduler)
        val useCase = GetItemsUseCase(mMockHiringItemsRepository, dispatcher)

        runTest(dispatcher) {
            val result = useCase.invoke()
            dispatcher.scheduler.advanceUntilIdle()
            assertTrue(result.isErr)
            assertTrue(result.error is FetchApiError.Unclassified)
        }
    }
}