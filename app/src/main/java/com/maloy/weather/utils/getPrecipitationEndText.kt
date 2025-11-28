package com.maloy.weather.utils

fun getPrecipitationEndText(precipitationType: String, endTime: String): String {
    val precipitationName = when {
        precipitationType.lowercase().contains("дождь") -> "Дождь"
        precipitationType.lowercase().contains("снег") -> "Снег"
        precipitationType.lowercase().contains("ливень") -> "Ливень"
        precipitationType.lowercase().contains("град") -> "Град"
        precipitationType.lowercase().contains("мокрый снег") -> "Мокрый снег"
        else -> "Осадки"
    }

    return "$precipitationName закончится в $endTime"
}