package com.maloy.weather.data

data class WeatherResponse(
    val location: Location,
    val current: CurrentWeather,
    val dayPhase: String,
    val weeklyForecast: List<WeeklyForecast> = emptyList(),
    val moonData: MoonData? = null,
    val sunTimes: SunTimes? = null
)