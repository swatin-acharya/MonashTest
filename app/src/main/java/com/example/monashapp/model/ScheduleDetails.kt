package com.example.monashapp.model

data class ScheduleDetails(
    val userName: String,
    val scheduleDataByDate: List<ScheduleByDate>)

data class ScheduleByDate(
    val date: String,
    val scheduleForDate: List<ScheduleForDate>
)

data class ScheduleForDate(
    val startTime: String,
    val endTime: String,
    val dueTime: String,
    val submittedTime: String,
    val courseName: String,
    val courseLocation: String,
    val courseStatus: String
)
