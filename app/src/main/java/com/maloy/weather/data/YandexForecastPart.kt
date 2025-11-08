package com.maloy.weather.data

data class YandexForecastPart(
    val temp_avg: Int?,
    val condition: String? = null,
    val wind_speed: Double? = null,
    val humidity: Int? = null,
    val prec_prob: Int? = null,
    val prec_mm: Double? = null,
    val temp_min: Int? = null,
    val temp_max: Int? = null,
    val is_day: Int = 1,
)