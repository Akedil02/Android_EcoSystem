package com.example.ecosystem.data.repository

import com.example.ecosystem.data.remote.CurrentWeather
import com.example.ecosystem.data.remote.WeatherApi
import com.example.ecosystem.data.remote.WeatherApiClient

class WeatherRepository(
    private val api: WeatherApi = WeatherApiClient.service
) {
    suspend fun getAlmatyWeather(): CurrentWeather = api.getCurrentWeather().current
}
