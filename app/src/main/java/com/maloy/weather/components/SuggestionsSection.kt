package com.maloy.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import com.maloy.weather.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.data.GeocodingSuggestion
import com.maloy.weather.utils.getConditionIcon
import com.maloy.weather.utils.rememberEnumPreference

@Composable
fun SuggestionsSection(
    suggestions: List<GeocodingSuggestion>,
    onSuggestionClick: (GeocodingSuggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    val (themeType) = rememberEnumPreference(themeType, defaultValue = ThemeType.GRADIENT)
    val isSystemDarkTheme = isSystemInDarkTheme()
    val textColor = when (themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.suggestions),
            style = MaterialTheme.typography.titleMedium,
            color = textColor.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            suggestions.forEach { suggestion ->
                SuggestionItem(
                    suggestion = suggestion,
                    onClick = { onSuggestionClick(suggestion) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun SuggestionItem(
    suggestion: GeocodingSuggestion,
    onClick: () -> Unit,
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(textColor.copy(alpha = 0.1f))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.location),
                contentDescription = null,
                tint = textColor.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = suggestion.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = textColor.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                )
                if (suggestion.description.isNotEmpty()) {
                    Text(
                        text = suggestion.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = textColor.copy(alpha = 0.6f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                getConditionIcon(suggestion.condition)?.let { icon ->
                    Text(
                        text = icon,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${suggestion.temperature}°",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = textColor.copy(alpha = 0.9f),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}