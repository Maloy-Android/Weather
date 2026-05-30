package com.maloy.weather.constans

sealed class Screen(val route: String) {
    object MAIN : Screen("main")
    object SEARCH : Screen("search")
    object SETTINGS : Screen("settings")
}