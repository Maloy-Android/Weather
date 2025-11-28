package com.maloy.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.data.SunTimes
import com.maloy.weather.utils.app.rememberEnumPreference

@Composable
fun SunTimesCard(sunTimes: SunTimes) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.sun_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            SunTimeline(sunTimes)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeLabel(stringResource(R.string.sunrise), sunTimes.sunrise)
                TimeLabel(stringResource(R.string.sunset), sunTimes.sunset)
            }
        }
    }
}

@Composable
private fun SunTimeline(sunTimes: SunTimes) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.15f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(sunTimes.dayProgress)
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFFB300),
                            Color(0xFFFFA000)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterStart)
        )
        Box(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = sunTimes.sunrise,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.offset(y = (-20).dp)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                text = sunTimes.sunset,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.offset(y = (-20).dp)
            )
        }
    }
}

@Composable
private fun TimeLabel(label: String, time: String) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = textColor.copy(alpha = 0.6f)
            )
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}