package com.maloy.weather.utils

import com.maloy.weather.data.YandexGeocodingResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface YandexGeocodingService {
    @GET("1.x")
    suspend fun geocode(
        @Query("geocode") query: String,
        @Query("apikey") apiKey: String,
        @Query("format") format: String = "json",
        @Query("lang") lang: String = "ru_RU",
        @Query("results") results: Int = 10
    ): YandexGeocodingResponse

    companion object {
        private const val BASE_URL = "https://geocode-maps.yandex.ru/"

        fun create(): YandexGeocodingService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(YandexGeocodingService::class.java)
        }
    }
}