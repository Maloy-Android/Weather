package com.maloy.weather.data

data class YandexForecast(
    val date: String,
    val date_ts: Long,
    val parts: YandexForecastParts,
    val hours: List<YandexHour>?,
    val moon_code: Int?,
    val sunrise: String?, // Добавляем поле восхода
    val sunset: String?
)