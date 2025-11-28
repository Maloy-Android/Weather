package com.maloy.weather.utils

import com.maloy.weather.data.HourlyForecast
import com.maloy.weather.data.yandex.YandexWeatherResponse

fun getHourlyForecast(weatherResponse: YandexWeatherResponse): List<HourlyForecast> {
    return try {
        val todayForecast = weatherResponse.forecasts.firstOrNull()
        val keyHours = listOf("0", "3", "6", "9", "12", "15", "18", "21")

        todayForecast?.hours?.filter { hour ->
            hour.hour in keyHours
        }?.map { hour ->
            HourlyForecast(
                time = formatHour(hour.hour),
                temperature = hour.temp,
                condition = mapYandexCondition(hour.condition)
            )
        } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}