package com.example.ecosystem.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val weather_code: Int
)

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double = 43.2383,
        @Query("longitude") longitude: Double = 76.9453,
        @Query("current") current: String = "temperature_2m,weather_code",
        @Query("timezone") timezone: String = "Asia/Almaty"
    ): WeatherResponse
}

object WeatherApiClient {
    val service: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}
