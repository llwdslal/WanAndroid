package com.rock.ui_home.route


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.*
import com.rock.ui_home.UiHome

sealed class HomeScreens(path: String ) : Screen<HomeScreens.ScreenRoutes>(path) {
    override val root: String
        get() = "home"

    object Index : HomeScreens("index")

    abstract class NavGraph(navController: NavController,screenRoutes: ScreenRoutes) : ScreenNavGraph(navController, Index) {

        override val composeScreens: NavGraphBuilder.() -> Unit = {
            composableScreen(Index.also { it.outRoutes = screenRoutes }) {
                UiHome(navController = this@NavGraph.navController)
            }
        }
    }

    interface ScreenRoutes:OutRoutes {
        fun startLoginForResult(requestCode:String):String
    }
}

object RouteRequestCode{
    const val Login = "RequestLoginForResult"
}



