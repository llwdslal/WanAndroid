package com.rock.lib_compose.arch

import androidx.navigation.NavOptionsBuilder

sealed class NavigationAction {
    class Nav(val route:String,val builder: NavOptionsBuilder.() -> Unit = {}):NavigationAction()
    object Back:NavigationAction()
}