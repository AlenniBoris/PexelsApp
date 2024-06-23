package com.example.pexapp.uikit.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pexapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    active: Boolean,
    query: String,
    history: Set<String>,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
){
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = if (active) 0.dp else 24.dp),
        query = query,
        onQueryChange = onQueryChanged,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChanged,
        placeholder = {
            Text(text = stringResource(id = R.string.search_string))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_icon),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = stringResource(id = R.string.search_icon_string)
            )
        },
        trailingIcon = {
            if (active){
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.icons_closed_string),
                    modifier = Modifier.clickable {
                        if (query.isNotBlank()){
                            onQueryChanged("")
                        } else {
                            onActiveChanged(false)
                        }
                    }
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary,
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    ) {
        LazyRow {
            items(items = history.toList()){
                HistoryIcon(onQueryChanged = onQueryChanged, text = it)
            }
        }
    }
}

@Composable
fun HistoryIcon(
    onQueryChanged: (String) -> Unit,
    text: String
){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onQueryChanged(text) }
    ) {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = Icons.Filled.History,
            contentDescription = stringResource(id = R.string.history_icon_string)
        )
        Text(text = text)
    }
}