package com.maloy.weather.data

data class SunTimes(
    val sunrise: String,
    val sunset: String,
    val dayDuration: String,
    val nightDuration: String,
    val dayProgress: Float
)