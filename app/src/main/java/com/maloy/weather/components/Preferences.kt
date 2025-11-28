@file:OptIn(ExperimentalMaterial3Api::class)

package com.maloy.weather.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maloy.weather.R
import com.maloy.weather.constans.ThemeType
import com.maloy.weather.constans.themeType
import com.maloy.weather.utils.app.rememberEnumPreference

@Composable
fun <T> ListPreference(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    selectedValue: T,
    values: List<T>,
    valueText: @Composable (T) -> String,
    onValueSelected: (T) -> Unit,
    isEnabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        ThemeSelectionDialog(
            onDismiss = { showDialog = false }
        ) {
            items(values) { value ->
                ThemeOptionCard(
                    isSelected = value == selectedValue,
                    title = valueText(value),
                    onClick = {
                        showDialog = false
                        onValueSelected(value)
                    }
                )
            }
        }
    }

    PreferenceCard(
        modifier = modifier,
        title = title,
        description = valueText(selectedValue),
        icon = icon,
        onClick = { showDialog = true },
        isEnabled = isEnabled,
        textColor = textColor
    )
}

@Composable
fun PreferenceCard(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    description: String? = null,
    icon: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    isEnabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = isEnabled && onClick != null,
                onClick = onClick ?: {}
            )
            .alpha(if (isEnabled) 1f else 0.5f),
        colors = CardDefaults.cardColors(
            containerColor = textColor.copy(alpha = 0.1f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    icon()
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                ProvideTextStyle(MaterialTheme.typography.titleMedium.copy(color = textColor)) {
                    title()
                }

                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = textColor.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.arrow_forward),
                contentDescription = null,
                tint = textColor.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ThemeSelectionDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    val (themeType) = rememberEnumPreference(
        themeType,
        defaultValue = ThemeType.GRADIENT
    )

    val isSystemDarkTheme = isSystemInDarkTheme()

    val backgroundColors = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.Black else Color.White
        ThemeType.DARK -> Color.Black
        ThemeType.LIGHT -> Color.White
        ThemeType.GRADIENT -> Color.Black
    }

    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.padding(24.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = backgroundColors,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_theme),
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )

                LazyColumn(
                    content = content,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ThemeOptionCard(
    isSelected: Boolean,
    title: String,
    onClick: () -> Unit
) {
    val (themeType) = rememberEnumPreference(
        themeType,
        defaultValue = ThemeType.GRADIENT
    )

    val isSystemDarkTheme = isSystemInDarkTheme()

    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
               textColor.copy(alpha = 0.2f)
            } else {
               textColor.copy(alpha = 0.05f)
            }
        ),
        shape = MaterialTheme.shapes.medium,
        border = if (isSelected) {
            BorderStroke(2.dp, textColor)
        } else {
            null
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(
                            alpha = 0.3f
                        ),
                        shape = CircleShape
                    )
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(3.dp)
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(textColor, CircleShape)
                    )
                }
            }

            Text(
                text = title,
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
inline fun <reified T : Enum<T>> EnumListPreference(
    modifier: Modifier = Modifier,
    noinline title: @Composable () -> Unit,
    noinline icon: (@Composable () -> Unit)? = null,
    selectedValue: T,
    noinline valueText: @Composable (T) -> String,
    noinline onValueSelected: (T) -> Unit,
    isEnabled: Boolean = true,
) {
    val (themeType) = rememberEnumPreference(
        themeType,
        defaultValue = ThemeType.GRADIENT
    )

    val isSystemDarkTheme = isSystemInDarkTheme()

    val textColor = when(themeType) {
        ThemeType.SYSTEM -> if (isSystemDarkTheme) Color.White else Color.Black
        ThemeType.DARK -> Color.White
        ThemeType.LIGHT -> Color.Black
        ThemeType.GRADIENT -> Color.White
    }
    ListPreference(
        modifier = modifier,
        title = title,
        icon = icon,
        selectedValue = selectedValue,
        values = enumValues<T>().toList(),
        valueText = valueText,
        onValueSelected = onValueSelected,
        isEnabled = isEnabled,
        textColor = textColor
    )
}