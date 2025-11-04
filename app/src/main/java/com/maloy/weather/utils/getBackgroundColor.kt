package com.maloy.weather.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.maloy.weather.viewModels.WeatherState

@Composable
fun getBackgroundColors(weatherState: WeatherState): List<Color> {
    val isSystemDarkTheme = isSystemInDarkTheme()
    val onBackgroundColor = if (!isSystemDarkTheme) listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)) else listOf(Color(0xFF000000), Color(0xFF000000))
    return when (weatherState) {
        is WeatherState.Success -> {
            val temp = weatherState.weather.current.temperature
            when {
                temp > 25 -> listOf(Color(0xFFF39C12), Color(0xFFE74C3C))
                temp > 15 -> listOf(Color(0xFF3498DB), Color(0xFF2ECC71))
                temp > 5 -> listOf(Color(0xFF2980B9), Color(0xFF3498DB))
                else -> listOf(Color(0xFF2C3E50), Color(0xFF34495E))
            }
        }
        else -> onBackgroundColor
    }
}