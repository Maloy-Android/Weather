package com.maloy.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maloy.weather.R
import com.maloy.weather.utils.getBackgroundColors
import com.maloy.weather.viewModels.WeatherState
import com.maloy.weather.viewModels.WeatherViewModel

@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = viewModel()) {
    var searchText by remember { mutableStateOf("") }
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val currentCity by weatherViewModel.currentCity.collectAsState()
    val focusManager = LocalFocusManager.current

    val backgroundGradient = Brush.verticalGradient(
        colors = getBackgroundColors(weatherState),
        startY = 0f,
        endY = 1000f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
            )

            if (weatherState is WeatherState.Idle || weatherState is WeatherState.Error) {
                SearchField(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    onSearch = {
                        if (searchText.isNotBlank()) {
                            weatherViewModel.loadWeather(searchText)
                            focusManager.clearFocus()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            when (val state = weatherState) {
                is WeatherState.Idle -> IdleState()
                is WeatherState.Loading -> LoadingState()
                is WeatherState.Success -> SuccessState(
                    weather = state.weather,
                    onRefresh = {
                        weatherViewModel.resetState()
                        searchText = ""
                    }
                )
                is WeatherState.Error -> ErrorState(
                    message = state.message,
                    onRetry = {
                        if (currentCity.isNotBlank()) {
                            weatherViewModel.loadWeather(currentCity)
                        }
                    }
                )
            }
        }
    }
}