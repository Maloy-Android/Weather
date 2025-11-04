package com.maloy.weather.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatHour(hourString: String): String {
    return try {
        val hour = hourString.toInt()
        String.format("%02d:00", hour)
    } catch (_: Exception) {
        hourString
    }
}