package com.maloy.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maloy.weather.data.WeatherDetailItem
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.utils.mapVisibility

@Composable
fun WeatherDetailsGrid(weather: WeatherResponse) {
    val details = listOf(
        WeatherDetailItem("💨 Ветер", "${weather.current.windSpeed.toInt()} м/с"),
        WeatherDetailItem("💧 Влажность", "${weather.current.humidity}%"),
        WeatherDetailItem("🌡️ Давление", "${weather.current.pressure} мм рт.ст."),
        WeatherDetailItem("👁️ Видимость", mapVisibility(weather.current.visibility))
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailCard(detail = details[0], modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            WeatherDetailCard(detail = details[1], modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailCard(detail = details[2], modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            WeatherDetailCard(detail = details[3], modifier = Modifier.weight(1f))
        }
    }
}