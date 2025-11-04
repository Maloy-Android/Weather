package com.maloy.weather.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maloy.weather.data.WeatherDetailItem

@Composable
fun WeatherDetailCard(detail: WeatherDetailItem, modifier: Modifier = Modifier) {
    val isSystemDarkTheme = isSystemInDarkTheme()
    val onBackgroundColor = if (!isSystemDarkTheme) Color.Black else Color.White
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = onBackgroundColor.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = detail.value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundColor
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = detail.label,
                style = MaterialTheme.typography.bodySmall.copy(color = onBackgroundColor.copy(alpha = 0.8f)),
                textAlign = TextAlign.Center
            )
        }
    }
}