package com.rock.ui_discovery.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.OutRoutes
import com.rock.lib_compose.navigation.Screen
import com.rock.lib_compose.navigation.ScreenNavGraph
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_discovery.UiDiscovery

sealed class DiscoveryScreens(path:String):Screen<OutRoutes>(path) {
    override val root: String
        get() = "discovery"

    object Index: DiscoveryScreens("index")

    abstract class NavGraph(navController: NavController):ScreenNavGraph(navController,Index){

         override val composeScreens: NavGraphBuilder.() -> Unit = {
            composableScreen(Index){
                UiDiscovery(this@NavGraph.navController)
            }
         }
     }
}

