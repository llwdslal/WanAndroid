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

    val homeGraph = remember { HomeGraph(navController) }
    val profileGraph = remember { ProfileGraph(navController) }
    val faqGraph = remember { FaqGraph(navController) }
    val projectGraph = remember { ProjectGraph(navController) }
    val squareGraph = remember { SquareGraph(navController) }
    val systemGraph = remember { SystemGraph(navController) }

    NavHost(modifier= modifier,navController = navController, startDestination = HomeScreens.Index.root){
        homeGraph.create(this)
        faqGraph.create(this)
        projectGraph.create(this)
        squareGraph.create(this)
        systemGraph.create(this)
        profileGraph.create(this)
    }
}

private class HomeGraph(navController: NavHostController) : HomeScreens.NavGraph(navController)

private class ProfileGraph(navController: NavHostController) : ProfileScreens.NavGraph(navController)

private class FaqGraph(navController: NavHostController) : FqaScreens.NavGraph(navController)

private class ProjectGraph(navController: NavHostController) : ProjectScreens.NavGraph(navController)

private class SquareGraph(navController: NavHostController) : SquareScreens.NavGraph(navController)

private class SystemGraph(navController: NavHostController) : SystemScreens.NavGraph(navController)