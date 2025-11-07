package com.maloy.weather.utils

fun getConditionIcon(condition: String): String {
    return when (condition) {
        "Ясно" -> "☀️"
        "Малооблачно" -> "🌤️"
        "Облачно с прояснениями" -> "⛅"
        "Пасмурно" -> "☁️"
        "Дождь", "Небольшой дождь" -> "🌧️"
        "Ливень" -> "⛈️"
        "Снег" -> "❄️"
        "Гроза" -> "🌩️"
        "Туман" -> "🌫️"
        else -> "🌈"
    }
}

fun getWeatherEmoji(condition: String): String {
    return when {
        condition.contains("Ясно") -> "☀️"
        condition.contains("Малооблачно") -> "🌤️"
        condition.contains("Облачно") -> "⛅"
        condition.contains("Пасмурно") -> "☁️"
        condition.contains("Дождь") -> "🌧️"
        condition.contains("Ливень") -> "⛈️"
        condition.contains("Снег") -> "❄️"
        condition.contains("Гроза") -> "🌩️"
        condition.contains("Туман") -> "🌫️"
        else -> "🌈"
    }
}