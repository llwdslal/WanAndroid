package com.rock.ui_profile.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.rock.lib_compose.navigation.OutRoutes
import com.rock.lib_compose.navigation.Screen
import com.rock.lib_compose.navigation.ScreenNavGraph
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_profile.UiProfile
import com.rock.ui_profile.login.Login

sealed class ProfileScreens(path:String):Screen<OutRoutes>(path) {
    override val root: String
        get() = "profile"



    object Index:ProfileScreens("index")
    object Login:ProfileScreens("login")


    abstract class NavGraph(navController : NavController):ScreenNavGraph(navController,Index){

        override val composeScreens: NavGraphBuilder.() -> Unit ={

            composableScreen(Index){
                UiProfile(this@NavGraph.navController)
            }

            composableScreen(Login){
                Login(this@NavGraph.navController)
            }
        }
    }
}

