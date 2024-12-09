package com.algee.fetchexercise.data.model

import com.algee.fetchexercise.api.model.HiringItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HiringItemStrictWrapperTest {

    @MockK
    lateinit var mMockBaseHiringItem: HiringItem

    private var mItem: HiringItemStrictWrapper? = null

    private fun useValidMockHiringItem() {
        every { mMockBaseHiringItem.name } returns "name"
        every { mMockBaseHiringItem.listId } returns 1
        every { mMockBaseHiringItem.id } returns 2
    }

    private fun useInvalidMockHiringItemNameIsNull() {
        every { mMockBaseHiringItem.name } returns null
        every { mMockBaseHiringItem.listId } returns 1
        every { mMockBaseHiringItem.id } returns 2
    }

    private fun useInvalidMockHiringItemNameIsEmpty() {
        every { mMockBaseHiringItem.name } returns ""
        every { mMockBaseHiringItem.listId } returns 1
        every { mMockBaseHiringItem.id } returns 2
    }

    private fun requireHiringItem(): HiringItemStrictWrapper = mItem!!

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getName() {
        useValidMockHiringItem()
        mItem = HiringItemStrictWrapper(mMockBaseHiringItem)
        assertEquals("name", requireHiringItem().name)
    }

    @Test
    fun getId() {
        useValidMockHiringItem()
        mItem = HiringItemStrictWrapper(mMockBaseHiringItem)
        assertEquals(2, requireHiringItem().id)
    }

    @Test
    fun getListId() {
        useValidMockHiringItem()
        mItem = HiringItemStrictWrapper(mMockBaseHiringItem)
        assertEquals(1, requireHiringItem().listId)
    }

    @Test
    fun createHiringItemStrictWhereTheBaseItemNameIsNullThrowsException() {
        useInvalidMockHiringItemNameIsNull()
        assertThrows<IllegalArgumentException> { mItem = HiringItemStrictWrapper(mMockBaseHiringItem) }
    }

    @Test
    fun createHiringItemStrictWhereBaseItemNameIsEmpty() {
        useInvalidMockHiringItemNameIsEmpty()
        mItem = HiringItemStrictWrapper(mMockBaseHiringItem)
        assertEquals("", requireHiringItem().name)
    }
}
