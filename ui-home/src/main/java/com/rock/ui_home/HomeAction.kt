package com.rock.ui_home

import com.rock.lib_base.arch.IAction

sealed class HomeAction:IAction {
    object Refresh:HomeAction()
}