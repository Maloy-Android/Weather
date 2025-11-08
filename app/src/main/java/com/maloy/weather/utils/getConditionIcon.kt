package com.maloy.weather.utils

import com.maloy.weather.data.YandexWeatherResponse

fun getConditionIcon(condition: String, weatherResponse: YandexWeatherResponse?): String? {
    val isDay = weatherResponse?.forecasts?.getOrNull(0)?.parts?.day?.is_day == 1
    return when (condition) {
        "Ясно" -> if (isDay) "☀️" else "🌙"
        "Облачно" -> "⛅"
        "Малооблачно" -> "🌤️"
        "Облачно с прояснениями" -> "⛅"
        "Пасмурно" -> "☁️"
        "Дождь" -> "🌧️"
        "Небольшой дождь" -> "🌧️"
        "Ливень" -> "⛈️"
        "Снег" -> "❄️"
        "Гроза" -> "🌩️"
        "Туман" -> "🌫️"
        else -> null
    }
}

fun getWeatherEmoji(condition: String, weatherResponse: YandexWeatherResponse?): String {
    val isDay = weatherResponse?.forecasts?.getOrNull(0)?.parts?.day?.is_day == 1
    return when {
        condition.contains("Ясно") -> if (isDay) "☀️" else "🌙"
        condition.contains("Облачно") -> "⛅"
        condition.contains("Малооблачно") -> "🌤️"
        condition.contains("Облачно с прояснениями") -> "⛅"
        condition.contains("Пасмурно") -> "☁️"
        condition.contains("Дождь") -> "🌧️"
        condition.contains("Небольшой дождь") -> "🌧️"
        condition.contains("Ливень") -> "⛈️"
        condition.contains("Снег") -> "❄️"
        condition.contains("Гроза") -> "🌩️"
        condition.contains("Туман") -> "🌫️"
        else -> null
    }!!
}