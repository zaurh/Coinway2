package com.zaurh.coinway.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zaurh.coinway.presentation.viewmodel.MainViewModel
import com.zaurh.coinway.R

@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    onSearch: (String) -> Unit = {},
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(hint != "") }


    Box(modifier = modifier) {
        BackHandler(enabled = text.isNotEmpty(), onBack = {
            text = ""
            mainViewModel.clearSearch()
            focusManager.clearFocus()
        })
        BasicTextField(
            cursorBrush = SolidColor(Color.White),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .shadow(5.dp)
                .background(colorResource(id = R.color.search_background))
                .padding(horizontal = 30.dp, vertical = 18.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 18.dp)
            )
        }
    }
}