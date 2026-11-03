package com.example.ecosystem.presentation

import com.example.ecosystem.domain.model.Trip

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(val items: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>
}

data class TripListUiState(
    val trips: List<Trip> = emptyList(),
    val result: UiState<List<Trip>> = UiState.Loading,
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val favoriteTripIds: Set<Int> = emptySet(),
    val joinedTripIds: Set<Int> = emptySet(),
    val weatherText: String? = null,
    val weatherLoading: Boolean = true,
    val weatherError: String? = null
) {
    val isLoading: Boolean
        get() = result is UiState.Loading

    val errorMessage: String?
        get() = (result as? UiState.Error)?.message
}
