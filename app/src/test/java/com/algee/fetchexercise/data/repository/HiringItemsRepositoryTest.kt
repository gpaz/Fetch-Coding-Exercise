package com.algee.fetchexercise.data.repository

import com.algee.fetchexercise.api.FetchApi
import com.algee.fetchexercise.api.model.HiringItemJson
import com.algee.fetchexercise.data.repository.error.FetchApiError
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import java.io.IOException

class HiringItemsRepositoryTest {

    private lateinit var mRepository: HiringItemsRepository

    @MockK
    private lateinit var mMockFetchApi: FetchApi

    @MockK
    private lateinit var mMockHttpException: HttpException

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mRepository = HiringItemsRepository(mMockFetchApi)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getHiringItemsReturnsASuccessfulResult() {
        coEvery { mMockFetchApi.getHiringItems() } returns SAMPLE_HIRING_ITEMS
        runTest {
            val result = mRepository.getHiringItems()
            assertTrue(result.isOk)
            assertEquals(SAMPLE_HIRING_ITEMS, result.value)
        }
    }

    @Test
    fun getHiringItemsReturnsAnErrorResultDueToIOException() {
        coEvery { mMockFetchApi.getHiringItems() } throws IOException("Test")
        runTest {
            val result = mRepository.getHiringItems()
            assertTrue(result.isErr)
            assertTrue(result.error is FetchApiError.NoInternet)
        }
    }

    @Test
    fun getHiringItemsReturnsAnErrorResultDueToHttpException() {
        coEvery { mMockFetchApi.getHiringItems() } throws mMockHttpException
        runTest {
            val result = mRepository.getHiringItems()
            assertTrue(result.isErr)
            assertTrue(result.error is FetchApiError.Http)
        }
    }

    @Test
    fun getHiringItemsReturnsAnErrorResultDueToGenericThrowable() {
        coEvery { mMockFetchApi.getHiringItems() } throws Throwable("Test")
        runTest {
            val result = mRepository.getHiringItems()
            assertTrue(result.isErr)
            assertTrue(result.error is FetchApiError.Unclassified)
        }
    }

    internal companion object {
        val SAMPLE_HIRING_ITEMS = listOf(
            HiringItemJson(
                id = 1,
                listId = 0,
                name = null
            ),
            HiringItemJson(
                id = 5,
                listId = 3,
                name = "Item 5"
            ),
            HiringItemJson(
                id = 2,
                listId = 2,
                name = "Item 2"
            ),
            HiringItemJson(
                id = 4,
                listId = 0,
                name = ""
            ),
            HiringItemJson(
                id = 3,
                listId = 1,
                name = ""
            ),
            HiringItemJson(
                id = 9,
                listId = 3,
                name = "Item 9"
            ),
            HiringItemJson(
                id = 10,
                listId = 0,
                name = ""
            ),
            HiringItemJson(
                id = 8,
                listId = 3,
                name = null
            ),
            HiringItemJson(
                id = 7,
                listId = 1,
                name = null
            ),
            HiringItemJson(
                id = 6,
                listId = 3,
                name = "Item 6"
            )
        )
    }
}