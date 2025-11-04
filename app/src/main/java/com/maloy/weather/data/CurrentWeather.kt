package com.maloy.weather.data

data class CurrentWeather(
    val temperature: Double,
    val condition: String,
    val windSpeed: Double,
    val humidity: Int,
    val feelsLike: Double,
    val pressure: Int,
    val visibility: Double
)