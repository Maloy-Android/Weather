package com.maloy.weather.constans

sealed class Screen(val route: String) {
    object MAIN : Screen("main")
    object SEARCH : Screen("search")
    object ABOUT : Screen("about")
    object SETTINGS : Screen("settings")
}