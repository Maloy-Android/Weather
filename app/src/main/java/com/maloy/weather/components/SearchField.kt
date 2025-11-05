package com.maloy.weather.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.maloy.weather.R

@Composable
fun SearchField(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    autoFocus: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(autoFocus) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { it.isFocused },
        placeholder = {
            Text(
                text = stringResource(R.string.enter_city_name),
                color = Color.White.copy(alpha = 0.7f)
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        singleLine = true,
        trailingIcon = {
            if (searchText.isNotBlank()) {
                IconButton(
                    onClick = {
                        onSearchTextChange("")
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.clear),
                        contentDescription = stringResource(R.string.clear),
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}