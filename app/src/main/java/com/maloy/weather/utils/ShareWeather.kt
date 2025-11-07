package com.maloy.weather.utils

import android.content.Context
import android.content.Intent
import com.maloy.weather.data.WeatherResponse

fun shareWeather(context: Context, weather: WeatherResponse) {
    val shareText = createWeatherShareText(weather)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Погода в ${weather.location.name}")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(intent, "Поделиться погодой"))
}

private fun createWeatherShareText(weather: WeatherResponse): String {
    return """
        🌤️ Погода в ${weather.location.name}
        Сейчас: ${weather.current.temperature.toInt()}°C, ${weather.current.condition}
        Ощущается как: ${weather.current.feelsLike.toInt()}°C
        💨 Ветер: ${weather.current.windSpeed.toInt()} м/с
        💧 Влажность: ${weather.current.humidity}%
        🌡️ Давление: ${weather.current.pressure} мм рт.ст.
        ☀️ УФ-индекс: ${weather.current.uvIndex}
        ${getWeatherEmoji(weather.current.condition, weatherResponse = null)} Сегодня: ${getDailyForecastSummary(weather)}
        #Погода #${weather.location.name.replace(" ", "")}
    """.trimIndent()
}

private fun getDailyForecastSummary(weather: WeatherResponse): String {
    return if (weather.weeklyForecast.isNotEmpty()) {
        val today = weather.weeklyForecast.first()
        "днём ${today.tempMax}°, ночью ${today.tempMin}°"
    } else {
        "прогноз недоступен"
    }
}