package com.maloy.weather.data.yandex

import com.maloy.weather.data.yandex.YandexForecastParts
import com.maloy.weather.data.yandex.YandexHour

data class YandexForecast(
    val date: String,
    val date_ts: Long,
    val parts: YandexForecastParts,
    val hours: List<YandexHour>?,
    val moon_code: Int?,
    val sunrise: String?,
    val sunset: String?
)