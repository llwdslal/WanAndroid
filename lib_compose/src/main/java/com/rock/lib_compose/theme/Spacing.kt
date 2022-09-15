package com.rock.lib_compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val normalPadding:Dp = 16.dp,
    val largePadding:Dp = 28.dp,
    val smallPadding:Dp = 8.dp,
    //todo add
)

val LocalSpacing = staticCompositionLocalOf<Spacing> { error("Spacing not provide") }
