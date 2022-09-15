package com.rock.wanandroid.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.rock.lib_compose.theme.WanAndroidTheme
import com.rock.lib_compose.theme.WindowCompatConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WanApp(){

    val wanAppState = rememberWanAppState()

    WanAndroidTheme(windowCompatConfig = object : WindowCompatConfig {
        override val designCompactSize: DpSize
            get() = DpSize(360.dp,640.dp)
        override val designMediumSize: DpSize?
            get() = null
        override val designExpandSize: DpSize?
            get() = null

    }) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

            Scaffold(
                topBar = {
                    if (wanAppState.shouldShowNavBar) {
                        TopBar(title = wanAppState.title, wanAppState::navigateToProfile)
                    }
                },
                bottomBar = {
                    if (wanAppState.shouldShowNavBar) {
                        BottomBar(wanAppState.bottomBarItems, wanAppState.selectedBottomItemIndex,wanAppState::navigateToBottomItem)
                    }
                },
                containerColor = Color.LightGray
            ) {
                WanNavHost(modifier = Modifier.padding(it),navController = wanAppState.navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(title: String, onProfileClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title ={ Text( text = title)},
        navigationIcon = {
            MaterialTheme
            IconButton(onClick = onProfileClick) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
            }
        },
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



