package com.rock.ui_home.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.Screen
import com.rock.lib_compose.navigation.ScreenNavGraph
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_home.UiHome

sealed class HomeScreens(path:String):Screen(path) {
    override val root: String
        get() = "home"

    object Index:HomeScreens("index")

    //abstract 是为涉及跨模块路由时定义抽象方法或属性
    abstract class NavGraph(navController: NavController):ScreenNavGraph(navController,Index){

         override val composeScreens: NavGraphBuilder.() -> Unit = {
            composableScreen(Index){
                UiHome()
            }
         }
     }
}

