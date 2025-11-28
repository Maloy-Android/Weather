package com.maloy.weather.data.yandex

data class YandexHour(
    val hour: String,
    val temp: Int,
    val condition: String,
    val prec_type: Int,
    val prec_strength: Double,
)