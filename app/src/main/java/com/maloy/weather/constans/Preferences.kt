package com.maloy.weather.constans

import androidx.datastore.preferences.core.stringPreferencesKey

val themeType = stringPreferencesKey("themeType")

enum class ThemeType {
    LIGHT, DARK, GRADIENT
}