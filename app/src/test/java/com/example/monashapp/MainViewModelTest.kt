package com.example.monashapp

import com.example.monashapp.model.ScheduleByDate
import com.example.monashapp.model.ScheduleDetails
import com.example.monashapp.model.ScheduleForDate
import com.example.monashapp.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import io.mockk.every

//Some tests for demo purpose using Mockk library.

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private lateinit var mockScheduleDetails: ScheduleDetails
    private lateinit var mockScheduleForDate: ScheduleForDate

    @Before
    fun setUp() {
        viewModel = MainViewModel()

        mockScheduleDetails = mockk()
        mockScheduleForDate = mockk()

        viewModel.scheduleDetails = mockScheduleDetails
        viewModel.parkingDetails = mockk() // Or mock parking details if necessary
    }



    @Test
    fun `test getNameOfUser returns userName when it is not null`() {
        // Given
        every { mockScheduleDetails.userName } returns "Swatin"

        // When
        val result = viewModel.getNameOfUser()

        // Then
        assertEquals("Swatin", result)
    }


    @Test
    fun `test getScheduleByDate returns schedule list when scheduleDataByDate is not null`() {
        val mockScheduleList = listOf(ScheduleByDate("Today 10 March", listOf()))
        every { mockScheduleDetails.scheduleDataByDate } returns mockScheduleList

        val result = viewModel.getScheduleByDate()

        assertEquals(mockScheduleList, result)
    }

    @Test
    fun `test getMainTimeText returns startTime when valid`() {
        every { mockScheduleForDate.startTime } returns "10:30"
        every { mockScheduleForDate.dueTime } returns "12:00"

        val result = viewModel.getMainTimeText(mockScheduleForDate)

        assertEquals("10:30", result)
    }

    @Test
    fun `test getMainTimeText returns dueTime when startTime is invalid`() {
        every { mockScheduleForDate.startTime } returns "invalid"
        every { mockScheduleForDate.dueTime } returns "12:00"

        val result = viewModel.getMainTimeText(mockScheduleForDate)

        assertEquals("12:00", result)
    }

    @Test
    fun `test getSecondaryText returns courseLocation when valid`() {
        every { mockScheduleForDate.courseLocation } returns "Room 101"
        every { mockScheduleForDate.courseStatus } returns "Online"

        val result = viewModel.getSecondaryText(mockScheduleForDate)

        assertEquals("Room 101", result)
    }

    @Test
    fun `test getSecondaryText returns courseStatus when courseLocation is invalid`() {
        every { mockScheduleForDate.courseLocation } returns "invalid"
        every { mockScheduleForDate.courseStatus } returns "Online"

        val result = viewModel.getSecondaryText(mockScheduleForDate)

        assertEquals("Online", result)
    }

}