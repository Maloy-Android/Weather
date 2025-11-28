package com.maloy.weather.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.utils.app.rememberEnumPreference

@Composable
fun LoadingState() {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = textColor,
            strokeWidth = 3.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.loading_weather_data),
            style = MaterialTheme.typography.bodyLarge.copy(color = textColor)
        )
    }
}