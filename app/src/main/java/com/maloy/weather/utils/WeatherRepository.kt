package com.maloy.weather.utils

import com.maloy.weather.data.CurrentWeather
import com.maloy.weather.data.WeeklyForecast
import com.maloy.weather.data.GeocodingSuggestion
import com.maloy.weather.data.Location
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.data.YandexWeatherResponse

class WeatherRepository {
    private val yandexGeocodingService = YandexGeocodingService.create()
    private val yandexWeatherService =
        YandexWeatherService.Companion.create("8f7cdf69-b220-46f6-8230-4fc569ed9f69")
    private val yandexGeocodingApiKey = "d6d3c9b5-dec8-45f4-aabc-2080d876b697"

    suspend fun getWeather(city: String): WeatherResponse? {
        try {
            val geocodingResponse = yandexGeocodingService.geocode(
                query = city,
                apiKey = yandexGeocodingApiKey
            )

            if (geocodingResponse.response.GeoObjectCollection.featureMember.isEmpty()) {
                throw Exception("Город '$city' не найден")
            }

            val weatherResponse = yandexWeatherService.getWeather(
                lat = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(" ")[1].toDouble(),
                lon = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(" ")[0].toDouble()
            )

            return WeatherResponse(
                location = Location(
                    name = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.name,
                    country = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.description ?: "",
                ),
                current = CurrentWeather(
                    temperature = weatherResponse.fact.temp.toDouble(),
                    condition = mapYandexCondition(weatherResponse.fact.condition),
                    windSpeed = weatherResponse.fact.wind_speed,
                    humidity = weatherResponse.fact.humidity,
                    feelsLike = weatherResponse.fact.feels_like.toDouble(),
                    pressure = weatherResponse.fact.pressure_mm,
                    uvIndex = weatherResponse.forecasts.firstOrNull()?.parts?.day?.uv_index ?: 0,
                    yesterdayTemperature = weatherResponse.forecasts.getOrNull(0)?.parts?.day?.temp_avg?.toDouble() ?: 0.0,
                    hourlyForecast = getHourlyForecast(weatherResponse)
                ),
                dayPhase = getDayPhase(weatherResponse),
                weeklyForecast = getWeeklyForecast(weatherResponse),
                moonData = getMoonData(weatherResponse.forecasts.firstOrNull()?.moon_code)
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
                query = query,
                apiKey = yandexGeocodingApiKey,
                results = 5
            )
            return geocodingResponse.response.GeoObjectCollection.featureMember.map { feature ->
                GeocodingSuggestion(
                    name = feature.GeoObject.name,
                    description = feature.GeoObject.description ?: "",
                    temperature = yandexWeatherService.getWeather(
                        lat = feature.GeoObject.Point.pos.split(
                            " "
                        )[1].toDouble(), lon = feature.GeoObject.Point.pos.split(" ")[0].toDouble()
                    ).fact.temp
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    private fun getWeeklyForecast(weatherResponse: YandexWeatherResponse): List<WeeklyForecast> {
        return weatherResponse.forecasts.mapIndexed { index, forecast ->
            WeeklyForecast(
                date = formatDateForDisplay(forecast.date),
                dayOfWeek = getDayOfWeek(forecast.date, index),
                tempMin = forecast.parts.night.temp_avg ?: forecast.parts.day.temp_min
                ?: forecast.parts.night.temp_min ?: 0,
                tempMax = forecast.parts.day.temp_avg ?: forecast.parts.day.temp_max
                ?: forecast.parts.night.temp_max ?: 0,
                condition = mapYandexCondition(
                    forecast.parts.day.condition ?: forecast.parts.night.condition ?: "clear"
                ),
                precipitation = forecast.parts.day.prec_prob ?: 0,
                windSpeed = forecast.parts.day.wind_speed ?: 0.0,
                humidity = forecast.parts.day.humidity ?: 0
            )
        }
    }
}