package com.maloy.weather.utils

fun isNightTime(time: String?): Boolean {
    if (time == null) return false
    return try {
        val hour = time.substringBefore(":").toInt()
        hour >= 21 || hour < 6
    } catch (_: Exception) {
        false
    }
}