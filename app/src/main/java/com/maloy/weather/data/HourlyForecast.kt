package com.maloy.weather.data

data class HourlyForecast(
    val time: String,
    val temperature: Int,
    val condition: String,
    val isDay: Boolean = true
)