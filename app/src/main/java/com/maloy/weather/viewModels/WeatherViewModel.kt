package com.maloy.weather.viewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.maloy.weather.data.GeocodingSuggestion
import com.maloy.weather.data.WeatherResponse
import com.maloy.weather.utils.SearchHistoryManager
import com.maloy.weather.utils.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WeatherRepository()
    private val searchHistoryManager = SearchHistoryManager(application.applicationContext)

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _currentCity = MutableStateFlow("")
    val currentCity: StateFlow<String> = _currentCity.asStateFlow()

    private val _searchHistory = mutableStateOf<List<String>>(emptyList())
    val searchHistory: State<List<String>> = _searchHistory

    private val _suggestions = mutableStateOf<List<GeocodingSuggestion>>(emptyList())
    val suggestions: State<List<GeocodingSuggestion>> = _suggestions

    private val _isLoadingSuggestions = mutableStateOf(false)
    val isLoadingSuggestions: State<Boolean> = _isLoadingSuggestions

    private var suggestionsJob: Job? = null

    init {
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            searchHistoryManager.searchHistory.collect { history ->
                _searchHistory.value = history
            }
        }
    }

    fun loadWeather(city: String) {
        if (city.isBlank()) return

        _weatherState.value = WeatherState.Loading
        _currentCity.value = city

        viewModelScope.launch {
            try {
                val weather = repository.getWeather(city)
                weather?.let {
                    _weatherState.value = WeatherState.Success(it)
                    addToSearchHistory(city)
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun resetState() {
        _weatherState.value = WeatherState.Idle
    }

    fun addToSearchHistory(city: String) {
        viewModelScope.launch {
            val currentHistory = _searchHistory.value.toMutableList()
            currentHistory.remove(city)
            currentHistory.add(0, city)
            if (currentHistory.size > 10) {
                currentHistory.removeAt(currentHistory.lastIndex)
            }
            _searchHistory.value = currentHistory
            searchHistoryManager.saveSearchHistory(currentHistory)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = emptyList()
            searchHistoryManager.clearSearchHistory()
        }
    }

    fun getSuggestions(query: String) {
        suggestionsJob?.cancel()
        if (query.length < 2) {
            _suggestions.value = emptyList()
            return
        }
        suggestionsJob = viewModelScope.launch {
            delay(300)
            _isLoadingSuggestions.value = true
            try {
                val suggestions = repository.getCitySuggestions(query)
                _suggestions.value = suggestions
            } catch (_: Exception) {
                _suggestions.value = emptyList()
            } finally {
                _isLoadingSuggestions.value = false
            }
        }
    }

    fun clearSuggestions() {
        suggestionsJob?.cancel()
        _suggestions.value = emptyList()
    }
}

sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}