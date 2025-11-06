@file:Suppress("DEPRECATION")

package com.maloy.weather.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun formatDateForDisplay(dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        date.format(formatter)
    } catch (_: Exception) {
        dateString
    }
}