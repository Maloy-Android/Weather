package com.maloy.weather.utils

import com.maloy.weather.data.CurrentWeather
import com.maloy.weather.data.Location
import com.maloy.weather.data.WeatherResponse

class WeatherRepository {
    private val yandexGeocodingService = YandexGeocodingService.create()
    private val yandexWeatherService = YandexWeatherService.Companion.create("8f7cdf69-b220-46f6-8230-4fc569ed9f69")
    private val yandexGeocodingApiKey = "d6d3c9b5-dec8-45f4-aabc-2080d876b697"

    suspend fun getWeather(city: String): WeatherResponse? {
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
                    visibility = weatherResponse.fact.visibility,
                    yesterdayTemperature = weatherResponse.forecasts.getOrNull(1)?.parts?.day?.temp_avg?.toDouble()
                        ?: return null
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Ошибка получения погоды: ${e.message ?: "Неизвестная ошибка"}")
        }
    }
}