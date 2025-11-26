package com.maloy.weather.utils

import com.maloy.weather.data.ApiKeyResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

interface ApiKeyService {
    suspend fun getApiKey(): ApiKeyResponse
}

class ApiKeyServiceImpl(private val client: HttpClient) : ApiKeyService {
    override suspend fun getApiKey(): ApiKeyResponse {
        val response: HttpResponse = client.get("https://raw.githubusercontent.com/Maloy-Android/api_key/main/api_key")
        return response.body()
    }
}