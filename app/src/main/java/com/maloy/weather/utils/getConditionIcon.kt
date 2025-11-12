package com.maloy.weather.utils

import com.maloy.weather.data.HourlyForecast

fun getConditionIcon(condition: String): String? {
    val isNight = isNightTimeNow()
    return when (condition) {
        "Ясно" -> if (isNight) "🌙" else "☀️"
        "Облачно" -> if (isNight) "🌙☁️" else "☁️"
        "Малооблачно" -> if (isNight) "🌙☁️" else "🌤️"
        "Облачно с прояснениями" -> if (isNight) "🌙☁️" else "🌤️"
        "Пасмурно" -> "☁️"
        "Дождь" -> if (isNight) "🌙🌧️" else "🌧️"
        "Небольшой дождь" -> if (isNight) "🌙🌧️" else "🌧️"
        "Ливень" -> "⛈️"
        "Снег" -> if (isNight) "🌙❄️" else "❄️"
        "Гроза" -> "🌩️"
        "Туман" -> "🌫️"
        else -> if (isNight) "🌙" else "☀️"
    }
}

fun getWeatherEmoji(condition: String): String {
    val isNight = isNightTimeNow()
    return when {
        condition.contains("Ясно") -> if (isNight) "🌙" else "☀️"
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

fun getWeeklyConditionIcon(condition: String): String? {
    return when (condition) {
        "Ясно" -> "☀️"
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

fun getHourlyConditionIcon(condition: String, forecast: HourlyForecast?): String? {
    val isNight = isNightTime(forecast?.time)
    return when (condition) {
        "Ясно" -> if (isNight) "🌙" else "☀️"
        "Малооблачно" -> if (isNight) "🌙☁️" else "🌤️"
        "Облачно с прояснениями" -> if (isNight) "🌙⛅" else "⛅"
        "Облачно" -> if (isNight) "🌙☁️" else "☁️"
        "Пасмурно" -> "☁️"
        "Дождь" -> if (isNight) "🌙🌧️" else "🌧️"
        "Небольшой дождь" -> if (isNight) "🌙🌧️" else "🌧️"
        "Ливень" -> "⛈️"
        "Снег" -> if (isNight) "🌙❄️" else "❄️"
        "Гроза" -> "🌩️"
        "Туман" -> "🌫️"
        else -> if (isNight) "🌙" else "☀️"
    }
}