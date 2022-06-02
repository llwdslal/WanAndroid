package com.rock.lib_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableScreen(
    screen: Screen,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = screen.route,
        arguments = screen.arguments,
        deepLinks = screen.deepLinks,
        content = content
    )
}
