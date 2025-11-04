package com.maloy.weather.utils

import com.maloy.weather.data.CurrentWeather
import com.maloy.weather.data.Location
import com.maloy.weather.data.WeatherResponse

class WeatherRepository {
    private val yandexGeocodingService = YandexGeocodingService.create()
    private val yandexWeatherService = YandexWeatherService.Companion.create("ВАШ API КЛЮЧ")
    private val yandexGeocodingApiKey = "ВАШ КЛЮЧ ГЕОКОДА"

    suspend fun getWeather(city: String): WeatherResponse {
        try {
            val geocodingResponse = yandexGeocodingService.geocode(
                city = city,
                apiKey = yandexGeocodingApiKey
            )

            if (geocodingResponse.response.GeoObjectCollection.featureMember.isEmpty()) {
                throw Exception("Город '$city' не найден")
            }

            val geoObject = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject
            val coordinates = geoObject.Point.pos.split(" ")
            if (coordinates.size < 2) {
                throw Exception("Неверные координаты для города '$city'")
            }

            val weatherResponse = yandexWeatherService.getWeather(lat = coordinates[1].toDouble(), lon =  coordinates[0].toDouble())

            return WeatherResponse(
                location = Location(
                    name = geoObject.name,
                    country = geoObject.description ?: "Россия",
                ),
                current = CurrentWeather(
                    temperature = weatherResponse.fact.temp.toDouble(),
                    condition = mapYandexCondition(weatherResponse.fact.condition),
                    windSpeed = weatherResponse.fact.wind_speed,
                    humidity = weatherResponse.fact.humidity,
                    feelsLike = weatherResponse.fact.feels_like.toDouble(),
                    pressure = weatherResponse.fact.pressure_mm,
                    visibility = weatherResponse.fact.visibility
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Ошибка получения погоды: ${e.message ?: "Неизвестная ошибка"}")
        }
    }
}