package com.rock.wanandroid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rock.ui_fqa.route.FqaScreens
import com.rock.ui_home.route.HomeScreens
import com.rock.ui_profile.route.ProfileScreens
import com.rock.ui_project.route.ProjectScreens
import com.rock.ui_square.route.SquareScreens
import com.rock.ui_system.route.SystemScreens


@Composable
fun WanNavHost(modifier: Modifier = Modifier,navController: NavHostController){

    NavHost(modifier= modifier,navController = navController, startDestination = HomeScreens.Index.root){
        HomeGraph(navController).create(this)
        FaqGraph(navController).create(this)
        ProjectGraph(navController).create(this)
        SquareGraph(navController).create(this)
        SystemGraph(navController).create(this)
        ProfileGraph(navController).create(this)
    }
}

private class HomeGraph(navController: NavHostController) : HomeScreens.NavGraph(navController)

private class ProfileGraph(navController: NavHostController) : ProfileScreens.NavGraph(navController)

private class FaqGraph(navController: NavHostController) : FqaScreens.NavGraph(navController)

private class ProjectGraph(navController: NavHostController) : ProjectScreens.NavGraph(navController)

private class SquareGraph(navController: NavHostController) : SquareScreens.NavGraph(navController)

private class SystemGraph(navController: NavHostController) : SystemScreens.NavGraph(navController)