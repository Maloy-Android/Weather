package com.maloy.weather.data.yandex

import com.maloy.weather.data.yandex.YandexPoint

data class YandexGeoObject(
    val name: String,
    val description: String?,
    val Point: YandexPoint
)