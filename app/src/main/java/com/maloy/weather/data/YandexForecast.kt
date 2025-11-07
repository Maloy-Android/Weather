package com.maloy.weather.data

data class YandexForecast(
    val date: String,
    val date_ts: Long,
    val parts: YandexForecastParts,
    val hours: List<YandexHour>?,
    val moon_code: Int?
)