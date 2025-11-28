package com.maloy.weather.utils.yandexweather

import android.content.Context
import com.maloy.weather.data.CurrentWeather
import com.maloy.weather.data.GeocodingSuggestion
import com.maloy.weather.data.Location
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.data.WeeklyForecast
import com.maloy.weather.data.yandex.YandexWeatherResponse
import com.maloy.weather.utils.apikey.ApiKeyManager
import com.maloy.weather.utils.apikey.ApiKeyServiceImpl
import com.maloy.weather.utils.SunTimesUtils
import com.maloy.weather.utils.formatDateForDisplay
import com.maloy.weather.utils.getDayOfWeek
import com.maloy.weather.utils.getDayPhase
import com.maloy.weather.utils.getHourlyForecast
import com.maloy.weather.utils.getMoonData
import com.maloy.weather.utils.apikey.httpClient
import com.maloy.weather.utils.mapYandexCondition

class WeatherRepository(context: Context) {
    private val apiKeyService = ApiKeyServiceImpl(httpClient)
    private val apiKeyManager = ApiKeyManager(context, apiKeyService)

    private var yandexWeatherService: YandexWeatherService? = null
    private val yandexGeocodingService = YandexGeocodingService.Companion.create()
    private val yandexGeocodingApiKey = "d6d3c9b5-dec8-45f4-aabc-2080d876b697"

    private suspend fun getWeatherService(): YandexWeatherService {
        if (yandexWeatherService == null) {
            val apiKey = apiKeyManager.getApiKey()
            yandexWeatherService = YandexWeatherService.Companion.create(apiKey)
        }
        return yandexWeatherService!!
    }

    private suspend fun refreshWeatherService() {
        try {
            val newApiKey = apiKeyManager.refreshApiKey()
            yandexWeatherService = YandexWeatherService.Companion.create(newApiKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getWeather(city: String): WeatherResponse? {
        try {
            val weatherService = getWeatherService()
            val geocodingResponse = yandexGeocodingService.geocode(
                query = city,
                apiKey = yandexGeocodingApiKey
            )

            if (geocodingResponse.response.GeoObjectCollection.featureMember.isEmpty()) {
                throw Exception("Город '$city' не найден")
            }

            val weatherResponse = weatherService.getWeather(
                lat = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(" ")[1].toDouble(),
                lon = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos.split(" ")[0].toDouble()
            )

            val todayForecast = weatherResponse.forecasts.firstOrNull()
            val sunTimes = todayForecast?.let { forecast ->
                if (forecast.sunrise != null && forecast.sunset != null) {
                    SunTimesUtils.calculateSunTimes(forecast.sunrise, forecast.sunset)
                } else {
                    null
                }
            }

            return WeatherResponse(
                location = Location(
                    name = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.name,
                    country = geocodingResponse.response.GeoObjectCollection.featureMember[0].GeoObject.description
                        ?: "",
                ),
                current = CurrentWeather(
                    temperature = weatherResponse.fact.temp.toDouble(),
                    condition = mapYandexCondition(weatherResponse.fact.condition),
                    windSpeed = weatherResponse.fact.wind_speed,
                    humidity = weatherResponse.fact.humidity,
                    feelsLike = weatherResponse.fact.feels_like.toDouble(),
                    pressure = weatherResponse.fact.pressure_mm,
                    uvIndex = weatherResponse.fact.uv_index ?: 0,
                    yesterdayTemperature = weatherResponse.forecasts.getOrNull(0)?.parts?.day?.temp_avg?.toDouble()
                        ?: 0.0,
                    hourlyForecast = getHourlyForecast(weatherResponse)
                ),
                dayPhase = getDayPhase(weatherResponse, forecast = null),
                weeklyForecast = getWeeklyForecast(weatherResponse),
                moonData = getMoonData(weatherResponse.forecasts.firstOrNull()?.moon_code),
                sunTimes = sunTimes
            )
        } catch (e: Exception) {
            if (e.message?.contains("api_key", ignoreCase = true) == true ||
                e.message?.contains("403", ignoreCase = true) == true ||
                e.message?.contains("401", ignoreCase = true) == true) {

                try {
                    refreshWeatherService()
                    return getWeather(city)
                } catch (_: Exception) {
                    e.printStackTrace()
                    throw Exception("Ошибка получения погоды: ${e.message ?: "Неизвестная ошибка"}")
                }
            } else {
                e.printStackTrace()
                throw Exception("Ошибка получения погоды: ${e.message ?: "Неизвестная ошибка"}")
            }
        }
    }

    suspend fun getCitySuggestions(query: String): List<GeocodingSuggestion> {
        try {
            if (query.length < 2) return emptyList()
            val weatherService = getWeatherService()
            val geocodingResponse = yandexGeocodingService.geocode(
                query = query,
                apiKey = yandexGeocodingApiKey,
                results = 5
            )

            return geocodingResponse.response.GeoObjectCollection.featureMember.map { feature ->
                val weatherResponse = weatherService.getWeather(
                    lat = feature.GeoObject.Point.pos.split(" ")[1].toDouble(),
                    lon = feature.GeoObject.Point.pos.split(" ")[0].toDouble()
                )
                GeocodingSuggestion(
                    name = feature.GeoObject.name,
                    description = feature.GeoObject.description ?: "",
                    temperature = weatherService.getWeather(
                        lat = feature.GeoObject.Point.pos.split(" ")[1].toDouble(),
                        lon = feature.GeoObject.Point.pos.split(" ")[0].toDouble()
                    ).fact.temp,
                    condition = mapYandexCondition(weatherResponse.fact.condition)
                )
            }
        } catch (e: Exception) {
            if (e.message?.contains("api_key", ignoreCase = true) == true ||
                e.message?.contains("403", ignoreCase = true) == true ||
                e.message?.contains("401", ignoreCase = true) == true) {

                try {
                    refreshWeatherService()
                    return getCitySuggestions(query)
                } catch (_: Exception) {
                    e.printStackTrace()
                    return emptyList()
                }
            } else {
                e.printStackTrace()
                return emptyList()
            }
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