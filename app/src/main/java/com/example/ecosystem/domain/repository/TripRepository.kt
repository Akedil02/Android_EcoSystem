package com.example.ecosystem.domain.repository

import com.example.ecosystem.domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): List<Trip>

    fun findTripById(id: Int): Trip?
}
