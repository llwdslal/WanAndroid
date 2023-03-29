package com.rock.lib_compose.arch

import androidx.navigation.NavOptionsBuilder


interface IAction //UI 层向 State 发送的事件

sealed class NavigationAction {
    //导航事件
    class Nav(val route:String,val builder: NavOptionsBuilder.() -> Unit = {}):NavigationAction()
    //导航返回
    object Back:NavigationAction()
}

