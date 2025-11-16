package com.example.pexapp.presentation.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.pexapp.R
import com.example.pexapp.presentation.screens.home.IHomeScreenIntent
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appContentPadding
import com.example.pexapp.presentation.uikit.theme.appSubtleColor
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.clickableElementBackground
import com.example.pexapp.presentation.uikit.theme.extraTextColor
import com.example.pexapp.presentation.uikit.theme.historyItemActiveBackgroundColor
import com.example.pexapp.presentation.uikit.theme.historyItemActiveTextColor
import com.example.pexapp.presentation.uikit.theme.historyItemDeleteButtonOuterPadding
import com.example.pexapp.presentation.uikit.theme.historyItemInnerPadding
import com.example.pexapp.presentation.uikit.theme.historyItemShape
import com.example.pexapp.presentation.uikit.theme.historyItemsHorizontalSpacing
import com.example.pexapp.presentation.uikit.theme.historyItemsVerticalSpacing
import com.example.pexapp.presentation.uikit.theme.historyListTopPadding
import com.example.pexapp.presentation.uikit.theme.homeScreenSearchBarShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    history: List<String>,
    isSearchHistoryVisible: Boolean,
    placeholder: String = "",
    proceedIntent: (IHomeScreenIntent) -> Unit
) {


    SearchBar(
        modifier = modifier,
        windowInsets = WindowInsets(0, 0, 0, 0),
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.padding(appContentPadding),
                query = query,
                onQueryChange = { newQuery ->
                    proceedIntent(
                        IHomeScreenIntent.ChangeQuery(newQuery)
                    )
                },
                onSearch = {
                    proceedIntent(
                        IHomeScreenIntent.ForceSearchPhotos
                    )
                },
                expanded = isSearchHistoryVisible,
                onExpandedChange = { isExpanded ->
                    proceedIntent(
                        IHomeScreenIntent.ChangeHistoryVisibility(isExpanded)
                    )
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        style = appTextStyle.copy(
                            color = appSubtleColor,
                            fontSize = appTextSize
                        )
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_icon),
                        tint = extraTextColor,
                        contentDescription = stringResource(R.string.picture_description)
                    )
                },
                trailingIcon = {
                    if (isSearchHistoryVisible) {
                        Icon(
                            modifier = Modifier.clickable {
                                proceedIntent(
                                    if (query.isNotEmpty()) IHomeScreenIntent.ClearQuery
                                    else IHomeScreenIntent.ChangeHistoryVisibility(false)
                                )
                            },
                            painter = painterResource(R.drawable.cancel_icon),
                            tint = baseTextColor,
                            contentDescription = stringResource(R.string.picture_description)
                        )
                    }
                },
                colors = TextFieldDefaults.colors().copy(
                    disabledTextColor = baseTextColor,
                    focusedTextColor = baseTextColor,
                    unfocusedTextColor = baseTextColor,
                    focusedContainerColor = clickableElementBackground,
                    disabledContainerColor = clickableElementBackground,
                    unfocusedContainerColor = clickableElementBackground,
                    cursorColor = baseTextColor,
                    textSelectionColors = TextSelectionColors(
                        handleColor = baseTextColor,
                        backgroundColor = appColor
                    )
                )
            )
        },
        expanded = isSearchHistoryVisible,
        onExpandedChange = { isHistoryVisible ->
            proceedIntent(
                IHomeScreenIntent.ChangeHistoryVisibility(isHistoryVisible)
            )
        },
        shape = homeScreenSearchBarShape,
        colors = SearchBarDefaults.colors(
            containerColor = appColor,
            dividerColor = baseTextColor
        )
    ) {
        FlowRow(
            modifier = Modifier
                .padding(historyListTopPadding)
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(appContentPadding),
            verticalArrangement = Arrangement.spacedBy(historyItemsVerticalSpacing),
            horizontalArrangement = Arrangement.spacedBy(historyItemsHorizontalSpacing)
        ) {
            history.forEach { searchItem ->
                HistoryItem(
                    modifier = Modifier
                        .clip(historyItemShape)
                        .background(
                            if (searchItem == query) historyItemActiveBackgroundColor
                            else clickableElementBackground
                        )
                        .padding(historyItemInnerPadding),
                    searchQuery = searchItem,
                    textColor = if (searchItem == query) historyItemActiveTextColor
                    else baseTextColor,
                    onTextClick = {
                        proceedIntent(
                            IHomeScreenIntent.ChangeQuery(searchItem)
                        )
                    },
                    onDeleteClick = {
                        proceedIntent(
                            IHomeScreenIntent.DeleteHistoryQuery(searchItem)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoryItem(
    modifier: Modifier,
    searchQuery: String,
    textColor: Color,
    onTextClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    onTextClick()
                }
                .weight(1f, false),
            text = searchQuery,
            style = appTextStyle.copy(
                color = textColor,
                fontSize = appTextSize
            ),
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            modifier = Modifier
                .padding(historyItemDeleteButtonOuterPadding)
                .clickable {
                    onDeleteClick()
                },
            painter = painterResource(R.drawable.cancel_icon),
            tint = textColor,
            contentDescription = stringResource(R.string.picture_description)
        )
    }
}

@Composable
@Preview
private fun LightTheme() {
    PexAppTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                HomeScreenSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(appContentPadding),
                    query = "bte",
                    placeholder = "placeholder",
                    history = List(70) { "$it&$it" },
                    isSearchHistoryVisible = true,
                    proceedIntent = {}
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PexAppTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                HomeScreenSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(appContentPadding),
                    query = "bte",
                    placeholder = "placeholder",
                    history = List(70) { "$it&$it" },
                    isSearchHistoryVisible = true,
                    proceedIntent = {}
                )
            }
        }
    }
}