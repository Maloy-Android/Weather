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