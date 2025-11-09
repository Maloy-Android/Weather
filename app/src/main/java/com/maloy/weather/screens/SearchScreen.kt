package com.maloy.weather.screens

import androidx.activity.compose.BackHandler
import com.maloy.weather.components.SearchField
import com.maloy.weather.components.SearchHistorySection
import com.maloy.weather.components.SuggestionsSection
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.utils.getBackgroundColors
import com.maloy.weather.utils.rememberEnumPreference
import com.maloy.weather.viewModels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }
    val searchHistory by weatherViewModel.searchHistory
    val suggestions by weatherViewModel.suggestions
    val isLoadingSuggestions by weatherViewModel.isLoadingSuggestions

    val weatherState by weatherViewModel.weatherState.collectAsState()
    val backgroundGradient: Brush = Brush.verticalGradient(
        colors = getBackgroundColors(weatherState),
        startY = 0f,
        endY = 1000f
    )

    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()

    val backgroundColors = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.Black else Color.White
        ThemeType.DARK -> Color.Black
        ThemeType.LIGHT -> Color.White
        ThemeType.GRADIENT -> backgroundGradient
    }

    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }

    LaunchedEffect(searchText) {
        weatherViewModel.getSuggestions(searchText)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                when (backgroundColors) {
                    is Brush -> Modifier.background(backgroundColors)
                    is Color -> Modifier.background(backgroundColors)
                    else -> Modifier
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            SearchField(
                searchText = searchText,
                onSearchTextChange = {
                    searchText = it
                    if (it.isEmpty()) {
                        weatherViewModel.clearSuggestions()
                    }
                },
                onSearch = {
                    if (searchText.isNotBlank()) {
                        onSearch(searchText)
                        weatherViewModel.clearSuggestions()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                autoFocus = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (suggestions.isNotEmpty()) {
                SuggestionsSection(
                    suggestions = suggestions,
                    isLoading = isLoadingSuggestions,
                    onSuggestionClick = { suggestion ->
                        searchText = suggestion.name
                        onSearch(suggestion.name)
                        weatherViewModel.clearSuggestions()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (searchHistory.isNotEmpty() && suggestions.isEmpty()) {
                SearchHistorySection(
                    searchHistory = searchHistory,
                    onSuggestionClick = { city ->
                        searchText = city
                        onSearch(city)
                        weatherViewModel.clearSuggestions()
                    },
                    onClearHistory = { weatherViewModel.clearSearchHistory() }
                )
            }
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
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = textColor
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick()
                            weatherViewModel.clearSuggestions()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
        BackHandler {
            onBackClick()
            weatherViewModel.clearSuggestions()
        }
    }
}