package com.rock.wanandroid.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rock.ui_fqa.UiFqa
import com.rock.ui_home.UiHome
import com.rock.ui_profile.UiProfile
import com.rock.ui_square.UiSquare
import com.rock.ui_system.UiSystem
import com.rock.wanandroid.ui.theme.WanAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WanApp(){

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
                    TopBar(title = bottomBarItems[selectedItemIndex].label) {

                    }
                },
                bottomBar = {
                    BottomBar(bottomBarItems, selectedItemIndex) {
                        selectedItemIndex = it
                    }
                },
                containerColor = Color.LightGray
            ) {
                Box(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()){
                    when(selectedItemIndex){
                        0 -> UiHome()
                        1 -> UiFqa()
                        2 -> UiProfile()
                        3 -> UiSquare()
                        else -> UiSystem()
                    }
                }
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
) {
    Home(
        "首页",
        { Icon(imageVector = Icons.Filled.Home, contentDescription = "") },
    ),
    Fqa(
        "问答",
        { Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
    ),
    Project(
        "项目",
        { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
    ),
    Square(
        "广场",
        { Icon(imageVector = Icons.Filled.Build, contentDescription = "") },
    ),
    System(
        "体系",
        { Icon(imageVector = Icons.Filled.Menu, contentDescription = "") },
    ),
}


