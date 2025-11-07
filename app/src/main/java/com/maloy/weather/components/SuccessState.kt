package com.maloy.weather.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.maloy.weather.data.WeatherResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessState(weather: WeatherResponse, onRefresh: () -> Unit) {
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            onRefresh()
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            WeatherHeader(weather = weather)

            Spacer(modifier = Modifier.height(16.dp))

            if (weather.current.hourlyForecast.isNotEmpty()) {
                HourlyForecastSection(forecasts = weather.current.hourlyForecast)
                Spacer(modifier = Modifier.height(16.dp))
            }

            WeatherDetailsGrid(weather = weather)

            Spacer(modifier = Modifier.height(16.dp))

            TodaySummaryCard(weather = weather)
            Spacer(modifier = Modifier.height(16.dp))

            if (weather.moonData != null) {
                MoonPhaseCard(moonData = weather.moonData)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (weather.weeklyForecast.isNotEmpty()) {
                WeeklyForecastCard(forecasts = weather.weeklyForecast)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        if (pullToRefreshState.isRefreshing) {
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-8).dp)
            )
        }
    }
}