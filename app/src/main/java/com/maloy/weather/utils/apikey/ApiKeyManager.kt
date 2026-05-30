package com.maloy.weather.utils.apikey

import android.content.Context
import androidx.core.content.edit
import com.maloy.weather.data.ApiKeyResponse
import java.util.Date

class ApiKeyManager(
    context: Context,
    private val apiKeyService: ApiKeyService
) {
    private val sharedPref = context.getSharedPreferences("api_key_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_API_KEY = "yandex_weather_api_key"
        private const val KEY_LAST_UPDATE = "last_api_key_update"
    }

    suspend fun getApiKey(): String {
        return try {
            val freshKey = fetchFreshApiKey()
            saveApiKey(freshKey.api_key)
            freshKey.api_key
        } catch (e: Exception) {
            e.printStackTrace()
            getStoredApiKey() ?: "66b6794f-5580-41d3-99d0-0e5fc9ea781d"
        }
    }

    suspend fun refreshApiKey(): String {
        val freshKey = fetchFreshApiKey()
        saveApiKey(freshKey.api_key)
        return freshKey.api_key
    }

    private suspend fun fetchFreshApiKey(): ApiKeyResponse {
        return apiKeyService.getApiKey()
    }

    private fun saveApiKey(apiKey: String) {
        sharedPref.edit {
            putString(KEY_API_KEY, apiKey)
                .putString(KEY_LAST_UPDATE, Date().toString())
        }
    }

    private fun getStoredApiKey(): String? {
        return sharedPref.getString(KEY_API_KEY, null)
    }
}