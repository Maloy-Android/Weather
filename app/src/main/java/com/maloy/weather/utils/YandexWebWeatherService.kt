package com.maloy.weather.utils

import com.maloy.weather.data.YandexWeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexWeatherService {
    @GET("v2/forecast")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 10,
        @Query("hours") hours: Boolean = true,
        @Query("extra") extra: Boolean = false
    ): YandexWeatherResponse

    companion object {
        private const val BASE_URL = "https://api.weather.yandex.ru/"

        fun create(apiKey: String): YandexWeatherService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-Yandex-API-Key", apiKey)
                        .build()
                    println("Sending request with API key")
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(YandexWeatherService::class.java)
        }
    }
}