package com.maloy.weather.utils

fun getDayPhase(weatherResponse: com.maloy.weather.data.YandexWeatherResponse): String {
    return try {
        if (weatherResponse.fact.is_day == 1) {
            when (weatherResponse.fact.condition) {
                "clear", "partly-cloudy" -> "day_clear"
                "cloudy", "overcast" -> "day_cloudy"
                "rain", "drizzle", "light-rain", "showers" -> "day_rain"
                "snow", "light-snow", "snow-showers" -> "day_snow"
                "thunderstorm" -> "day_thunderstorm"
                else -> "day_clear"
            }
        } else {
            when (weatherResponse.fact.condition) {
                "clear", "partly-cloudy" -> "night_clear"
                "cloudy", "overcast" -> "night_cloudy"
                "rain", "drizzle", "light-rain", "showers" -> "night_rain"
                "snow", "light-snow", "snow-showers" -> "night_snow"
                "thunderstorm" -> "night_thunderstorm"
                else -> "night_clear"
            }
        }
    } catch (_: Exception) {
        "day_clear"
    }
}