package com.rock.ui_system.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.Screen
import com.rock.lib_compose.navigation.ScreenNavGraph
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_system.UiSystem

sealed class SystemScreens(path:String):Screen(path) {
    override val root: String
        get() = "square"

    object Index: SystemScreens("index")

    abstract class NavGraph(navController: NavController):ScreenNavGraph(navController,Index){

         override val composeScreens: NavGraphBuilder.() -> Unit = {
            composableScreen(Index){
                UiSystem()
            }
         }
     }
}

