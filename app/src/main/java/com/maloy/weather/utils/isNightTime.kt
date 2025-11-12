package com.maloy.weather.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime

fun isNightTime(time: String?): Boolean {
    if (time == null) return false
    return try {
        val hour = time.substringBefore(":").toInt()
        hour >= 21 || hour < 6
    } catch (_: Exception) {
        false
    }
}

@SuppressLint("NewApi")
fun isNightTimeNow(): Boolean {
    return try {
        val currecntHour = LocalDateTime.now().hour
        currecntHour >= 21 || currecntHour < 6
    } catch (_: Exception) {
        false
    }
}