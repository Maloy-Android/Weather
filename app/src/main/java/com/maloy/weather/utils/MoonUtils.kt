package com.maloy.weather.utils

import com.maloy.weather.data.MoonData

fun getMoonData(moonCode: Int?): MoonData {
    return when (moonCode) {
        0 -> MoonData(
            phase = "Новолуние",
            illumination = 0,
            phaseCode = moonCode
        )
        1 -> MoonData(
            phase = "Растущая луна",
            illumination = 25,
            phaseCode = moonCode
        )
        2 -> MoonData(
            phase = "Первая четверть",
            illumination = 50,
            phaseCode = moonCode
        )
        3 -> MoonData(
            phase = "Прибывающая луна",
            illumination = 75,
            phaseCode = moonCode
        )
        4 -> MoonData(
            phase = "Полнолуние",
            illumination = 100,
            phaseCode = moonCode
        )
        5 -> MoonData(
            phase = "Убывающая луна",
            illumination = 75,
            phaseCode = moonCode
        )
        6 -> MoonData(
            phase = "Последняя четверть",
            illumination = 50,
            phaseCode = moonCode
        )
        7 -> MoonData(
            phase = "Старая луна",
            illumination = 25,
            phaseCode = moonCode
        )
        else -> MoonData(
            phase = "Новолуние",
            illumination = 0,
            phaseCode = 0
        )
    }
}

fun getMoonEmoji(phaseCode: Int): String {
    return when (phaseCode) {
        0 -> "🌑"
        1 -> "🌒"
        2 -> "🌓"
        3 -> "🌔"
        4 -> "🌕"
        5 -> "🌖"
        6 -> "🌗"
        7 -> "🌘"
        else -> "🌑"
    }
}