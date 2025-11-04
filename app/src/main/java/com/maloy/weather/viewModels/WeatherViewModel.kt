package com.maloy.weather.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.utils.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _currentCity = MutableStateFlow("")
    val currentCity = _currentCity.asStateFlow()

    fun loadWeather(city: String) {
        if (city.isBlank()) return

        _weatherState.value = WeatherState.Loading
        _currentCity.value = city

        viewModelScope.launch {
            try {
                val weather = repository.getWeather(city)
                _weatherState.value = WeatherState.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun resetState() {
        _weatherState.value = WeatherState.Idle
    }
}

sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}