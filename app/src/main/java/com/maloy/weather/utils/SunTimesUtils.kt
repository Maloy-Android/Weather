@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.maloy.weather.utils

import com.maloy.weather.data.SunTimes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object SunTimesUtils {

    fun calculateSunTimes(sunrise: String, sunset: String): SunTimes {
        val sunriseTime = parseTime(sunrise)
        val sunsetTime = parseTime(sunset)
        val currentTime = Calendar.getInstance()

        val dayDuration = calculateDuration(sunriseTime, sunsetTime)
        val nightDuration = calculateDuration(sunsetTime, sunriseTime)
        val dayProgress = calculateDayProgress(sunriseTime, sunsetTime, currentTime)

        return SunTimes(
            sunrise = sunrise,
            sunset = sunset,
            dayDuration = formatDuration(dayDuration),
            nightDuration = formatDuration(nightDuration),
            dayProgress = dayProgress
        )
    }

    private fun parseTime(timeString: String): Calendar {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        calendar.time = timeFormat.parse(timeString)
        return calendar
    }

    private fun calculateDuration(start: Calendar, end: Calendar): Int {
        val startMinutes = start.get(Calendar.HOUR_OF_DAY) * 60 + start.get(Calendar.MINUTE)
        val endMinutes = end.get(Calendar.HOUR_OF_DAY) * 60 + end.get(Calendar.MINUTE)

        return if (endMinutes >= startMinutes) {
            endMinutes - startMinutes
        } else {
            (24 * 60 - startMinutes) + endMinutes
        }
    }

    private fun calculateDayProgress(sunrise: Calendar, sunset: Calendar, current: Calendar): Float {
        val sunriseMinutes = sunrise.get(Calendar.HOUR_OF_DAY) * 60 + sunrise.get(Calendar.MINUTE)
        val sunsetMinutes = sunset.get(Calendar.HOUR_OF_DAY) * 60 + sunset.get(Calendar.MINUTE)
        val currentMinutes = current.get(Calendar.HOUR_OF_DAY) * 60 + current.get(Calendar.MINUTE)

        val dayDuration = if (sunsetMinutes >= sunriseMinutes) {
            sunsetMinutes - sunriseMinutes
        } else {
            (24 * 60 - sunriseMinutes) + sunsetMinutes
        }

        val minutesSinceSunrise = if (currentMinutes >= sunriseMinutes) {
            currentMinutes - sunriseMinutes
        } else {
            (24 * 60 - sunriseMinutes) + currentMinutes
        }

        return (minutesSinceSunrise.toFloat() / dayDuration.toFloat()).coerceIn(0f, 1f)
    }

    private fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return if (mins > 0) "${hours}ч ${mins}м" else "${hours}ч"
    }
}