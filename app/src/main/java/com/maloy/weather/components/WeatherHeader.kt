package com.maloy.weather.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.data.WeatherResponse

@Composable
fun WeatherHeader(weather: WeatherResponse) {
    val isSystemDarkTheme = isSystemInDarkTheme()
    val onBackgroundColor = if (!isSystemDarkTheme) Color.Black else Color.White
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = onBackgroundColor.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${weather.location.name}, ${weather.location.country}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = onBackgroundColor
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${weather.current.temperature.toInt()}°",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = onBackgroundColor
                )
            )

            Text(
                text = weather.current.condition,
                style = MaterialTheme.typography.titleLarge.copy(color = onBackgroundColor.copy(alpha = 0.9f)),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = stringResource(R.string.feels_like, weather.current.feelsLike.toInt()),
                style = MaterialTheme.typography.bodyLarge.copy(color = onBackgroundColor.copy(alpha = 0.8f)),
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(R.string.yesterday_at_this_time, weather.current.yesterdayTemperature.toInt()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = onBackgroundColor.copy(
                        alpha = 0.7f
                    )
                ),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}