package com.rock.lib_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

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

abstract class ScreenNavGraph(protected val navController: NavController,private val startScreen:Screen){

    protected abstract val composeScreens: NavGraphBuilder.() -> Unit

    fun create(builder: NavGraphBuilder){
        builder.run {
            navigation(startDestination = startScreen.route,route = startScreen.root){
                composeScreens.invoke(this)
            }
        }
    }
}
