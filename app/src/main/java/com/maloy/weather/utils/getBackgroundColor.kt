package com.maloy.weather.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.maloy.weather.viewModels.WeatherState

@Composable
fun getBackgroundColors(weatherState: WeatherState): List<Color> {
    return when (weatherState) {
        is WeatherState.Success -> {
            getDynamicBackground(weatherState.weather.dayPhase)
        }
        else -> {
            listOf(Color(0xFF2C3E50), Color(0xFF34495E))
        }
    }
}

private fun getDynamicBackground(dayPhase: String): List<Color> {
    return when (dayPhase) {
        "day_clear" -> listOf(Color(0xFF64B5F6), Color(0xFF1976D2))
        "day_cloudy" -> listOf(Color(0xFF90A4AE), Color(0xFF546E7A))
        "day_rain" -> listOf(Color(0xFF78909C), Color(0xFF455A64))
        "day_snow" -> listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
        "day_thunderstorm" -> listOf(Color(0xFF37474F), Color(0xFF263238))
        "night_clear" -> listOf(Color(0xFF0D47A1), Color(0xFF1A237E))
        "night_cloudy" -> listOf(Color(0xFF37474F), Color(0xFF263238))
        "night_rain" -> listOf(Color(0xFF263238), Color(0xFF1A1A1A))
        "night_snow" -> listOf(Color(0xFF37474F), Color(0xFF1A237E))
        "night_thunderstorm" -> listOf(Color(0xFF1A1A1A), Color(0xFF000000))
        else -> listOf(Color(0xFF1A1A1A), Color(0xFF000000))
    }
}