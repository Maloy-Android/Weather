package com.maloy.weather.data.yandex

import com.maloy.weather.data.yandex.YandexInfo

data class YandexWeatherResponse(
    val fact: YandexFact,
    val info: YandexInfo,
    val forecasts: List<YandexForecast>,
)