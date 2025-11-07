package com.maloy.weather.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maloy.weather.components.WeatherApp
import com.maloy.weather.constans.Screen
import com.maloy.weather.screens.AboutScreen
import com.maloy.weather.screens.SearchScreen
import com.maloy.weather.viewModels.WeatherViewModel

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.MAIN) }
    val weatherViewModel: WeatherViewModel = viewModel()
    val context = LocalContext.current

    var hasRequestedPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.all { it }) {
            weatherViewModel.loadWeatherByLocation(context)
        } else {
            weatherViewModel.resetState()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasRequestedPermission) {
            hasRequestedPermission = true

            if (PermissionUtils.hasLocationPermission(context)) {
                weatherViewModel.loadWeatherByLocation(context)
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    when (currentScreen) {
        Screen.MAIN -> WeatherApp(
            onAboutClick = { currentScreen = Screen.ABOUT },
            onSearchClick = { currentScreen = Screen.SEARCH },
            weatherViewModel = weatherViewModel
        )

        Screen.SEARCH -> SearchScreen(
            onBackClick = { currentScreen = Screen.MAIN },
            onSearch = { query ->
                if (query.isNotBlank()) {
                    weatherViewModel.loadWeather(query)
                    currentScreen = Screen.MAIN
                }
            },
            weatherViewModel = weatherViewModel
        )

        Screen.ABOUT -> AboutScreen(
            onBackClick = { currentScreen = Screen.MAIN }
        )
    }
}