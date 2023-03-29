package com.rock.lib_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable

private const val TAG = "NavGraphKtx"

fun NavGraphBuilder.composableScreen(
    screen: Screen<out OutRoutes>,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = screen.route,
        arguments = screen.argumentsWithRequestCode,
        deepLinks = screen.deepLinks,
        content = content
    )
}

abstract class ScreenNavGraph(protected val navController: NavController,private val startScreen:Screen<out OutRoutes>){

    protected abstract val composeScreens: NavGraphBuilder.() -> Unit

    fun create(builder: NavGraphBuilder){
        builder.run {
            navigation(startDestination = startScreen.route,route = startScreen.root){
                composeScreens.invoke(this)
            }
        }
    }
}
