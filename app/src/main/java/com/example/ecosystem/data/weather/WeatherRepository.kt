package com.example.ecosystem.data.weather

class WeatherRepository(
    private val api: WeatherApi = WeatherApiClient.service
) {
    suspend fun getAlmatyWeather(): CurrentWeather = api.getCurrentWeather().current
}
