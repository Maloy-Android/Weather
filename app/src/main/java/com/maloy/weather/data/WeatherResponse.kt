package com.maloy.weather.data

data class WeatherResponse(
    val location: Location,
    val current: CurrentWeather
)