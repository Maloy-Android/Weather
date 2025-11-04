package com.maloy.weather.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.data.WeatherResponse

@Composable
fun SuccessState(weather: WeatherResponse, onRefresh: () -> Unit) {
    val isSystemDarkTheme = isSystemInDarkTheme()
    val onBackgroundColor = if (!isSystemDarkTheme) Color.Black else Color.White
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherHeader(weather = weather)

        Spacer(modifier = Modifier.height(32.dp))

        WeatherDetailsGrid(weather = weather)

        Spacer(modifier = Modifier.height(32.dp))

        FloatingActionButton(
            onClick = onRefresh,
            containerColor = onBackgroundColor,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.home),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}