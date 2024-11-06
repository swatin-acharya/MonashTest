package com.example.monashapp.model

data class ParkingDetails(val parkingZone: String, val parkingSubZones: List<ParkingSubZone>)

data class ParkingSubZone(val subZone: String, val numberOfSpots: String)
