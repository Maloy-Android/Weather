package com.maloy.weather.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maloy.weather.components.BottomNavigationBar
import com.maloy.weather.components.WeatherApp
import com.maloy.weather.constans.Screen
import com.maloy.weather.screens.SearchScreen
import com.maloy.weather.screens.SettingsScreen
import com.maloy.weather.utils.app.PermissionUtils
import com.maloy.weather.viewModels.WeatherViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = viewModel()
    val context = LocalContext.current

    var hasRequestedPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }

        if (allGranted) {
            weatherViewModel.loadWeatherByLocation(context)
        } else {
            val hasLocation = PermissionUtils.hasLocationPermission(context)
            if (hasLocation) {
                weatherViewModel.loadWeatherByLocation(context)
            } else {
                weatherViewModel.resetState()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!hasRequestedPermission) {
            hasRequestedPermission = true
            val hasLocation = PermissionUtils.hasLocationPermission(context)
            val hasNotifications = PermissionUtils.hasNotificationPermission(context)
            if (hasLocation && hasNotifications) {
                weatherViewModel.loadWeatherByLocation(context)
            } else if (hasLocation) {
                weatherViewModel.loadWeatherByLocation(context)
            } else {
                permissionLauncher.launch(PermissionUtils.getRequiredPermissions())
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                weatherViewModel = weatherViewModel,
                iconColor = Color.LightGray,
                selectedIconColor = Color.White
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.MAIN.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.MAIN.route) {
                WeatherApp(
                    weatherViewModel = weatherViewModel,
                    navController = navController
                )
            }

            composable(Screen.SEARCH.route) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() },
                    onSearch = { query ->
                        if (query.isNotBlank()) {
                            weatherViewModel.loadWeather(query)
                            navController.navigate(Screen.MAIN.route) {
                                popUpTo(Screen.MAIN.route) { inclusive = false }
                            }
                        }
                    },
                    weatherViewModel = weatherViewModel
                )
            }

            composable(Screen.SETTINGS.route) {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    weatherViewModel = weatherViewModel
                )
            }
        }
    }
}