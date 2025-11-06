package com.maloy.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maloy.weather.R
import com.maloy.weather.utils.getBackgroundColors
import com.maloy.weather.viewModels.WeatherState
import com.maloy.weather.viewModels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
    onAboutClick: () -> Unit,
    onSearchClick: () -> Unit,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val currentCity by weatherViewModel.currentCity.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val backgroundGradient: Brush = Brush.verticalGradient(
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
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            when (val state = weatherState) {
                is WeatherState.Idle -> IdleState()
                is WeatherState.Loading -> LoadingState()
                is WeatherState.Success -> SuccessState(
                    weather = state.weather,
                    onRefresh = {
                        if (currentCity.isNotBlank()) {
                            weatherViewModel.loadWeather(currentCity)
                        }
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
            Spacer(modifier = Modifier.height(32.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f), Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopCenter)
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    when {
                        weatherState is WeatherState.Success || weatherState is WeatherState.Error -> {
                            IconButton(
                                onClick = {
                                    weatherViewModel.resetState()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_back),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                        else -> {
                            Spacer(modifier = Modifier.size(48.dp))
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = stringResource(R.string.search),
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = onAboutClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    }
}