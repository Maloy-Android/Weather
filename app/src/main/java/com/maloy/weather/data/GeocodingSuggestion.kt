package com.maloy.weather.data

data class GeocodingSuggestion(
    val name: String,
    val description: String,
    val temperature: Int,
    val condition: String = "Clear"
)