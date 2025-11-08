package com.maloy.weather.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.utils.rememberEnumPreference
import kotlin.math.abs

@Composable
fun TodaySummaryCard(weather: WeatherResponse) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val textColor = when(themeType) {
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = textColor.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.today),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            val tempDiff = weather.current.temperature - weather.current.yesterdayTemperature
            val diffText = when {
                tempDiff > 0 -> stringResource(R.string.warmer_than_yesterday, abs(tempDiff).toInt())
                tempDiff < 0 -> stringResource(R.string.colder_than_yesterday, abs(tempDiff).toInt())
                else -> stringResource(R.string.same_as_yesterday)
            }
            val diffEmoji = when {
                tempDiff > 1 -> "🔺"
                tempDiff < -1 -> "🔻"
                else -> "➡️"
            }

            Text(
                text = "$diffEmoji $diffText",
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )

            if (weather.weeklyForecast.isNotEmpty()) {
                val precipitation = weather.weeklyForecast.first().precipitation
                if (precipitation > 0) {
                    Text(
                        text = "💧 ${stringResource(R.string.precipitation_chance, precipitation)}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = textColor),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}