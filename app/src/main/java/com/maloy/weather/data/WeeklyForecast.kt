package com.maloy.weather.data

data class WeeklyForecast(
    val date: String,
    val dayOfWeek: String,
    val tempMin: Int,
    val tempMax: Int,
    val condition: String,
    val precipitation: Int,
    val windSpeed: Double,
    val humidity: Int
)