package com.rock.wanandroid.ui

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rock.lib_compose.arch.ComposeState
import com.rock.lib_compose.arch.resources
import com.rock.ui_fqa.route.FqaScreens
import com.rock.ui_home.route.HomeScreens
import com.rock.ui_project.route.ProjectScreens
import com.rock.ui_square.route.SquareScreens
import com.rock.ui_system.route.SystemScreens
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberWanAppState(
    navController: NavHostController = rememberNavController(),
    selectedBottomBarIndexState:MutableState<Int> = remember { mutableStateOf(0) },
    resources: Resources = resources(),
    coroutineScope:CoroutineScope = rememberCoroutineScope()
) =  remember(navController,selectedBottomBarIndexState){
    WanAppState(selectedBottomBarIndexState,navController,resources,coroutineScope)
}

class WanAppState(
    private val selectedBottomItemState:MutableState<Int>,
    override val navController: NavHostController,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
    ):ComposeState() {
    val bottomBarItems = BottomBarItem.values()

    private val bottomBarRoutes = bottomBarItems.map { it.route }

    val shouldShowNavBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
    val selectedBottomItemIndex
        get() = selectedBottomItemState.value

    val title:String
        get() = bottomBarItems[selectedBottomItemIndex].label

    fun navigateToBottomItem(index: Int) {
        if (index != selectedBottomItemIndex) {
            selectedBottomItemState.value = index
            navController.navigate(bottomBarItems[index].route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateToProfile(){
        //测试 PageNotFound
        this.navigate("abc"){}
    }

}

enum class BottomBarItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val route: String
) {
    Home(
        "首页",
        { Icon(imageVector = Icons.Filled.Home, contentDescription = "") },
        HomeScreens.Index.route
    ),
    Fqa(
        "问答",
        { Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
        FqaScreens.Index.route
    ),
    Project(
        "项目",
        { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
        ProjectScreens.Index.route
    ),
    Square(
        "广场",
        { Icon(imageVector = Icons.Filled.Build, contentDescription = "") },
        SquareScreens.Index.route
    ),
    System(
        "体系",
        { Icon(imageVector = Icons.Filled.Menu, contentDescription = "") },
        SystemScreens.Index.route
    ),
}