package com.maloy.weather.data

data class CurrentWeather(
    val temperature: Double,
    val condition: String,
    val windSpeed: Double,
    val humidity: Int,
    val feelsLike: Double,
    val pressure: Int,
    val uvIndex: Int,
    val yesterdayTemperature: Double,
    val hourlyForecast: List<HourlyForecast> = emptyList()
)