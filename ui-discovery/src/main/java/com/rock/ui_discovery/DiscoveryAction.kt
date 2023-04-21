package com.rock.ui_discovery

import com.rock.lib_compose.arch.IAction

sealed class DiscoveryAction :IAction {

   object RefreshList : DiscoveryAction()

   class SelectTab(val index:Int):DiscoveryAction()
}