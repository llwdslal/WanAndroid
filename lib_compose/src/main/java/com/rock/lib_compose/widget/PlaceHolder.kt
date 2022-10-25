package com.rock.lib_compose.widget

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.defaultPlaceHolder(visible:Boolean) = this.placeholder(
    visible = visible,
    color = Color.Gray,
    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White)
)