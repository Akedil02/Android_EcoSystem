package com.example.ecosystem.ui.screens

import com.example.ecosystem.data.model.Trip

data class TripListUiState(
    val trips: List<Trip> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val favoriteTripIds: Set<Int> = emptySet(),
    val joinedTripIds: Set<Int> = emptySet(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
