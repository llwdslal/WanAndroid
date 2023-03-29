package com.rock.ui_profile

import com.rock.lib_compose.arch.IAction


sealed class ProfileAction: IAction {
    object Logout:ProfileAction()
}
