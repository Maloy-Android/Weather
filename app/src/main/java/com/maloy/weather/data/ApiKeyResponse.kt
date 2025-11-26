package com.maloy.weather.data


data class ApiKeyResponse(
    val api_key: String,
    val expires_at: String? = null
)