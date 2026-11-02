package com.example.ecosystem.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecosystem.data.TripRepository
import com.example.ecosystem.data.model.Trip
import com.example.ecosystem.data.weather.WeatherRepository
import com.example.ecosystem.ui.screens.TripListUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

class TripListViewModel(
    private val repository: TripRepository = TripRepository(),
    private val weatherRepository: WeatherRepository = WeatherRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(TripListUiState())
    val uiState: StateFlow<TripListUiState> = _uiState.asStateFlow()
    private var allTrips: List<Trip> = emptyList()

    init {
        Log.d(TAG, "init")
        refreshTrips()
        refreshWeather()
    }

    fun onSearchChange(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                trips = filterTrips(query, state.selectedCategory)
            )
        }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { state ->
            state.copy(
                selectedCategory = category,
                trips = filterTrips(state.searchQuery, category)
            )
        }
    }

    fun onFavouriteClick(tripId: Int) {
        _uiState.update { state ->
            val favorites = state.favoriteTripIds.toMutableSet()
            if (!favorites.add(tripId)) favorites.remove(tripId)
            state.copy(favoriteTripIds = favorites)
        }
    }

    fun onJoinClick(tripId: Int) {
        _uiState.update { state ->
            state.copy(joinedTripIds = state.joinedTripIds + tripId)
        }
    }

    fun onRetry() = refreshTrips()

    fun onWeatherRetry() = refreshWeather()

    fun tripById(tripId: Int): Trip? = repository.findTripById(tripId)

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    private fun refreshTrips() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                allTrips = repository.getTrips()
                _uiState.update { state ->
                    state.copy(
                        trips = filterTrips(state.searchQuery, state.selectedCategory),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        trips = emptyList(),
                        isLoading = false,
                        errorMessage = exception.message ?: "Unable to load trips."
                    )
                }
            }
        }
    }

    private fun refreshWeather() {
        _uiState.update { it.copy(weatherLoading = true, weatherError = null) }
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getAlmatyWeather()
                _uiState.update {
                    it.copy(
                        weatherText = "${weather.temperature_2m} °C · ${weatherDescription(weather.weather_code)}",
                        weatherLoading = false,
                        weatherError = null
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: IOException) {
                _uiState.update {
                    it.copy(weatherLoading = false, weatherError = "No internet connection. Check your connection and retry.")
                }
            } catch (exception: HttpException) {
                _uiState.update {
                    it.copy(weatherLoading = false, weatherError = "Weather server error (${exception.code()}). Please retry.")
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(weatherLoading = false, weatherError = "Unable to load weather. Please retry.")
                }
            }
        }
    }

    private fun weatherDescription(code: Int): String = when (code) {
        0 -> "Clear"
        1, 2, 3 -> "Partly cloudy"
        45, 48 -> "Foggy"
        in 51..67 -> "Rain"
        in 71..77 -> "Snow"
        in 80..82 -> "Showers"
        in 95..99 -> "Thunderstorm"
        else -> "Current weather in Almaty"
    }

    private fun filterTrips(query: String, category: String): List<Trip> {
        val normalizedQuery = query.trim()
        return allTrips.filter { trip ->
            val matchesCategory = category == ALL_CATEGORY || trip.category == category
            val matchesQuery = normalizedQuery.isEmpty() ||
                trip.title.contains(normalizedQuery, ignoreCase = true) ||
                trip.driver.contains(normalizedQuery, ignoreCase = true) ||
                trip.pickupPoint.contains(normalizedQuery, ignoreCase = true) ||
                trip.destination.contains(normalizedQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    private companion object {
        const val TAG = "SIS4ViewModel"
        const val ALL_CATEGORY = "All"
    }
}
