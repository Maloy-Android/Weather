package com.maloy.weather.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maloy.weather.R
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.utils.app.rememberEnumPreference

@Composable
fun SearchHistorySection(
    searchHistory: List<String>,
    onSuggestionClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.search_history),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
            TextButton(
                onClick = onClearHistory
            ) {
                Text(
                    text = stringResource(R.string.clear_history),
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            searchHistory.forEach { city ->
                SearchHistoryItem(
                    city = city,
                    onClick = { onSuggestionClick(city) }
                )
            }
        }
    }
}