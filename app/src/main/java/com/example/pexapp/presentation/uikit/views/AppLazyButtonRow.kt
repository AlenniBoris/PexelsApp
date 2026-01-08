package com.example.pexapp.presentation.uikit.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexapp.presentation.uikit.model.ClickableElement
import com.example.pexapp.presentation.uikit.theme.PexAppTheme
import com.example.pexapp.presentation.uikit.theme.appColor
import com.example.pexapp.presentation.uikit.theme.appRoundedShape
import com.example.pexapp.presentation.uikit.theme.appTextSize
import com.example.pexapp.presentation.uikit.theme.appTextStyle
import com.example.pexapp.presentation.uikit.theme.baseTextColor
import com.example.pexapp.presentation.uikit.theme.clickableElementBackground
import com.example.pexapp.presentation.uikit.theme.lazyButtonInnerPadding
import com.example.pexapp.presentation.uikit.theme.supportButtonsBackgroundColor
import com.example.pexapp.presentation.uikit.theme.supportButtonsTextColor

@Composable
fun AppLazyButtonRow(
    modifier: Modifier = Modifier,
    currentElement: ClickableElement,
    listOfElements: List<ClickableElement>,
    itemsLazyListState: LazyListState = rememberLazyListState(),
) {

    LaunchedEffect(key1 = currentElement) {
        val index = listOfElements.indexOfFirst { it.text == currentElement.text }
        if (index >= 0) {
            itemsLazyListState.animateScrollToItem(index)
        }
    }

    LazyRow(
        modifier = modifier,
        state = itemsLazyListState,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(listOfElements) { element ->

            val backgroundColor by animateColorAsState(
                if (element == currentElement)
                    supportButtonsBackgroundColor
                else
                    clickableElementBackground
            )

            val textColor by animateColorAsState(
                if (element == currentElement)
                    supportButtonsTextColor
                else
                    baseTextColor
            )

            Box(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .clickable {
                        element.onClick()
                    }
                    .background(color = backgroundColor)
                    .padding(lazyButtonInnerPadding)
            ) {

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = element.text,
                    style = appTextStyle.copy(
                        color = textColor,
                        fontSize = appTextSize
                    )
                )
            }
        }
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

                AppLazyButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .background(appColor),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "4",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "5",
                            onClick = {}
                        )
                    )
                )

                AppLazyButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .background(appColor),
                    currentElement = ClickableElement(
                        text = "woidjoiqd1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1wkmdk",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "qwdkmwqdlkwd2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3wql",
                            onClick = {}
                        )
                    )
                )

                AppLazyButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .background(appColor),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        )
                    )
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

                AppLazyButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .background(appColor),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        ), ClickableElement(
                            text = "3",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "4",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "5",
                            onClick = {}
                        )
                    )
                )

                AppLazyButtonRow(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .clip(appRoundedShape)
                        .fillMaxWidth()
                        .background(appColor),
                    currentElement = ClickableElement(
                        text = "1",
                        onClick = {}
                    ),
                    listOfElements = listOf(
                        ClickableElement(
                            text = "1",
                            onClick = {}
                        ),
                        ClickableElement(
                            text = "2",
                            onClick = {}
                        )
                    )
                )
            }
        }
    }
}