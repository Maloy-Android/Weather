package com.maloy.weather.data.yandex

data class YandexFact(
    val temp: Int,
    val feels_like: Int,
    val condition: String,
    val wind_speed: Double,
    val pressure_mm: Int,
    val humidity: Int,
    val visibility: Double,
    val uv_index: Int?,
    val prec_type: Int, // тип осадков (0 - нет, 1 - дождь, 2 - дождь со снегом, 3 - снег, 4 - град)
    val prec_strength: Double
)