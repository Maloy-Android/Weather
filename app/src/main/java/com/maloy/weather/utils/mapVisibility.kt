package com.maloy.weather.utils

fun mapVisibility(visibilityCode: Double): String {
    return when (visibilityCode.toInt()) {
        0 -> "Отличная"
        1 -> "Хорошая"
        2 -> "Средняя"
        3 -> "Плохая"
        4 -> "Очень плохая"
        5 -> "Туман"
        6 -> "Густой туман"
        7 -> "Очень густой туман"
        8 -> "Дымка"
        9 -> "Мгла"
        else -> "Неизвестно"
    }
}