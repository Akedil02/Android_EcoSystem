package com.example.ecosystem.data.model

data class Trip(
    val id: Int,
    val title: String,
    val driver: String,
    val time: String,
    val seats: Int,
    val co2Saved: Double,
    val category: String,
    val pickupPoint: String,
    val destination: String
)
