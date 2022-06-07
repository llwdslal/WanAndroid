package com.rock.ui_square.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.Screen
import com.rock.lib_compose.navigation.ScreenNavGraph
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_square.UiSquare

sealed class SquareScreens(path:String):Screen(path) {
    override val root: String
        get() = "square"

    object Index: SquareScreens("index")

    abstract class NavGraph(navController: NavController):ScreenNavGraph(navController,Index){

         override val composeScreens: NavGraphBuilder.() -> Unit = {
            composableScreen(Index){
                UiSquare()
            }
         }
     }
}

