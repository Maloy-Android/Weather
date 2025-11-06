@file:Suppress("DEPRECATION")

package com.maloy.weather.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun getDayOfWeek(dateString: String, index: Int): String {
    return try {
        val date = LocalDate.parse(dateString)
        when (index) {
            0 -> "Сегодня"
            1 -> "Завтра"
            else -> {
                val formatter = DateTimeFormatter.ofPattern("EEEE", Locale("ru"))
                val dayName = date.format(formatter).replaceFirstChar { it.uppercase() }
                when (dayName) {
                    "Понедельник" -> "Пн"
                    "Вторник" -> "Вт"
                    "Среда" -> "Ср"
                    "Четверг" -> "Чт"
                    "Пятница" -> "Пт"
                    "Суббота" -> "Сб"
                    "Воскресенье" -> "Вс"
                    else -> dayName
                }
            }
        }
    } catch (_: Exception) {
        when (index) {
            0 -> "Сегодня"
            1 -> "Завтра"
            2 -> "Послезавтра"
            else -> "День ${index + 1}"
        }
    }
}