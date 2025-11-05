package com.maloy.weather.utils

import com.maloy.weather.data.CurrentWeather
import com.maloy.weather.data.GeocodingSuggestion
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
            val dayPhase = getDayPhase(weatherResponse)

            return WeatherResponse(
                location = Location(
                    name = geoObject.name,
                    country = geoObject.description ?: "",
                ),
                current = CurrentWeather(
                    temperature = weatherResponse.fact.temp.toDouble(),
                    condition = mapYandexCondition(weatherResponse.fact.condition),
                    windSpeed = weatherResponse.fact.wind_speed,
                    humidity = weatherResponse.fact.humidity,
                    feelsLike = weatherResponse.fact.feels_like.toDouble(),
                    pressure = weatherResponse.fact.pressure_mm,
                    uvIndex = weatherResponse.forecasts.firstOrNull()?.parts?.day?.uv_index ?: return null,
                    yesterdayTemperature = weatherResponse.forecasts.getOrNull(0)?.parts?.day?.temp_avg?.toDouble()
                        ?: return null,
                    hourlyForecast = getHourlyForecast(weatherResponse)
                ),
                dayPhase = dayPhase
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Ошибка получения погоды: ${e.message ?: "Неизвестная ошибка"}")
        }
    }
    suspend fun getCitySuggestions(query: String): List<GeocodingSuggestion> {
        try {
            if (query.length < 2) return emptyList()
            val geocodingResponse = yandexGeocodingService.geocode(
                city = query,
                apiKey = yandexGeocodingApiKey,
                results = 5
            )
            return geocodingResponse.response.GeoObjectCollection.featureMember.map { feature ->
                val geoObject = feature.GeoObject
                GeocodingSuggestion(
                    name = geoObject.name,
                    description = geoObject.description ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}