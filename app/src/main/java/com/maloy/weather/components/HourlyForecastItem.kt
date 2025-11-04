package com.maloy.weather.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maloy.weather.data.HourlyForecast
import com.maloy.weather.utils.getConditionIcon

@Composable
fun HourlyForecastItem(forecast: HourlyForecast, onBackgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Text(
            text = forecast.time,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = onBackgroundColor.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${forecast.temperature}°",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = onBackgroundColor
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = getConditionIcon(forecast.condition),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}