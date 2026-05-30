package com.maloy.weather.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.maloy.weather.R
import com.maloy.weather.constans.BottomNavItem
import com.maloy.weather.constans.Screen
import com.maloy.weather.viewModels.WeatherViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    iconColor: Color,
    selectedIconColor: Color
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem(
            route = Screen.MAIN.route,
            title = R.string.home,
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = Screen.SEARCH.route,
            title = R.string.search,
            icon = Icons.Default.Search
        ),
        BottomNavItem(
            route = Screen.SETTINGS.route,
            title = R.string.settings,
            icon = Icons.Default.Settings
        )
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(bottom = 8.dp),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.MAIN.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title),
                        tint = if (isSelected) selectedIconColor else iconColor
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        color = if (isSelected) selectedIconColor else iconColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = selectedIconColor,
                    selectedTextColor = selectedIconColor,
                    unselectedIconColor = iconColor,
                    unselectedTextColor = iconColor
                )
            )
        }
    }
}