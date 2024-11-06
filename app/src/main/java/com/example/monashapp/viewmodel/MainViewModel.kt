package com.example.monashapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.monashapp.model.ParkingDetails
import com.example.monashapp.model.ParkingSubZone
import com.example.monashapp.model.ScheduleByDate
import com.example.monashapp.model.ScheduleDetails
import com.example.monashapp.model.ScheduleForDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var scheduleDetails: ScheduleDetails? = null
    var parkingDetails: List<ParkingDetails>? = null

    init {
        //this viewmodel will do api fetching for all details required in this screen
        //when initialised. Here dummy data is fed for example. This data will actually come from api
        val schedule1ForDate1 = ScheduleForDate(
            "10:30",
            "1:30",
            "invalid",
            "invalid",
            "FIT2001: TUTORIAL",
            "S4 13 College Walk, Clayton",
            "invalid"
        )
        val schedule2ForDate1 = ScheduleForDate(
            "invalid",
            "invalid",
            "5PM",
            "5PM",
            "MTK1000: Weekly quizzes",
            "invalid",
            "Submitted"
        )
        val listOfSchedulesForDate1 = listOf(schedule1ForDate1, schedule2ForDate1)

        val schedule1ForDate2 = ScheduleForDate(
            "invalid",
            "invalid",
            "5PM",
            "5PM",
            "FIT2050: In-class quizzes",
            "invalid",
            "Submission Closes"
        )
        val listOfSchedulesForDate2 = listOf(schedule1ForDate2)

        val scheduleByDate1 = ScheduleByDate("Today 10 March", listOfSchedulesForDate1)
        val scheduleByDate2 = ScheduleByDate("Sun, 12 March", listOfSchedulesForDate2)

        val scheduleByDateList = listOf(scheduleByDate1, scheduleByDate2)

        scheduleDetails =
            ScheduleDetails(userName = "Swatin", scheduleDataByDate = scheduleByDateList)


        val parkingSubZone1 = ParkingSubZone("B", "12")
        val parkingSubZone2 = ParkingSubZone("R", "5")
        val parkingSubZone1List = listOf(parkingSubZone1, parkingSubZone2)

        val parkingSubZone3 = ParkingSubZone("B", "0")
        val parkingSubZone4 = ParkingSubZone("R", "2")
        val parkingSubZoneList2 = listOf(parkingSubZone3, parkingSubZone4)

        val parkingDetails1 = ParkingDetails("North (multi-level)", parkingSubZone1List)
        val parkingDetails2 = ParkingDetails("West 1", parkingSubZoneList2)

        parkingDetails = listOf(parkingDetails1, parkingDetails2)
    }

    //this function returns the name of user or empty if not available
    fun getNameOfUser(): String {
        return scheduleDetails?.userName ?: ""
    }

    //this returns the list of schedules
    fun getScheduleByDate(): List<ScheduleByDate> {
        return scheduleDetails?.scheduleDataByDate ?: emptyList()
    }

    fun getMainTimeText(schedule: ScheduleForDate): String {
        return if (isValid(schedule.startTime)) schedule.startTime else schedule.dueTime
    }

    fun isValid(value: String): Boolean {
        return value != "invalid" && value != ""
    }

    fun getSecondaryText(schedule: ScheduleForDate): String {
        return if (isValid(schedule.courseLocation)) schedule.courseLocation else schedule.courseStatus
    }

    fun getAllParkingDetails(): List<ParkingDetails> {
        return parkingDetails ?: emptyList()
    }
}