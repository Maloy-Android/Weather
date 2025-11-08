package com.maloy.weather.data

data class YandexFact(
    val temp: Int,
    val feels_like: Int,
    val condition: String,
    val wind_speed: Double,
    val pressure_mm: Int,
    val humidity: Int,
    val visibility: Double,
    val uv_index: Int?
)