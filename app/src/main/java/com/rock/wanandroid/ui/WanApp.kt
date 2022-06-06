package com.rock.wanandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.rock.ui_fqa.route.FqaScreens
import com.rock.ui_home.route.HomeScreens
import com.rock.ui_project.route.ProjectScreens
import com.rock.ui_square.route.SquareScreens
import com.rock.ui_system.route.SystemScreens
import com.rock.wanandroid.ui.theme.WanAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WanApp(){

    val navController = rememberNavController()
    var selectedItemIndex by remember { mutableStateOf(0) }
    val bottomBarItems = remember { BottomBarItem.values() }

    WanAndroidTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopBar(title = bottomBarItems[selectedItemIndex].label) {}
                },
                bottomBar = {
                    BottomBar(bottomBarItems, selectedItemIndex) {
                        if (it != selectedItemIndex) {
                            selectedItemIndex = it
                            navController.navigate(bottomBarItems[selectedItemIndex].route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                containerColor = Color.LightGray
            ) {
                WanNavHost(modifier = Modifier.padding(it),navController = navController)
            }
        }
    }
}

@Composable
internal fun TopBar(title: String, onProfileClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title ={ Text( text = title)},
        navigationIcon = {
            IconButton(onClick = onProfileClick) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
            }
        }
    )
}

@Composable
internal fun BottomBar(items:Array<BottomBarItem>,selectedIndex:Int = 0, onBottomItemClick:(Int) -> Unit) {
    NavigationBar {
        items.forEachIndexed { index, bottomBarItem ->
            NavigationBarItem(
                icon = bottomBarItem.icon,
                selected = index == selectedIndex,
                onClick = {onBottomItemClick(index)},
                colors = NavigationBarItemDefaults.colors()// 设置颜色
            )
        }
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
        HomeScreens.Index.root
    ),
    Fqa(
        "问答",
        { Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
        FqaScreens.Index.root
    ),
    Project(
        "项目",
        { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
        ProjectScreens.Index.root
    ),
    Square(
        "广场",
        { Icon(imageVector = Icons.Filled.Build, contentDescription = "") },
        SquareScreens.Index.root
    ),
    System(
        "体系",
        { Icon(imageVector = Icons.Filled.Menu, contentDescription = "") },
        SystemScreens.Index.root
    ),
}


