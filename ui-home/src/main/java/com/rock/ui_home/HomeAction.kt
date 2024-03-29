package com.rock.ui_home

import com.rock.lib_compose.arch.IAction


sealed class HomeAction: IAction {
    object RefreshList:HomeAction()
    object ToListTop:HomeAction()

    class CollectArticle(val id:Int):HomeAction()
}