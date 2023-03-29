package com.rock.wanandroid.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rock.lib_compose.navigation.PageNotFoundScreen
import com.rock.lib_compose.navigation.composableScreen
import com.rock.ui_fqa.route.FqaScreens
import com.rock.ui_home.route.HomeScreens
import com.rock.ui_profile.route.ProfileScreens
import com.rock.ui_project.route.ProjectScreens
import com.rock.ui_square.route.SquareScreens
import com.rock.ui_system.route.SystemScreens


@Composable
fun WanNavHost(modifier: Modifier = Modifier,navController: NavHostController){

    NavHost(modifier= modifier,navController = navController, startDestination = HomeScreens.Index.root){
        HomeGraph(navController,object :HomeScreens.ScreenRoutes{
            override fun startLoginForResult(requestCode: String): String {
                return ProfileScreens.Login.createForResultRoute(requestCode)
            }


        }).create(this)
        FaqGraph(navController).create(this)
        ProjectGraph(navController).create(this)
        SquareGraph(navController).create(this)
        SystemGraph(navController).create(this)
        ProfileGraph(navController).create(this)

        // 404 页面
        composableScreen(PageNotFoundScreen){
            PageNotFound(navController = navController)
        }
    }
}

private class HomeGraph(navController: NavHostController, homeRouters: HomeScreens.ScreenRoutes) : HomeScreens.NavGraph(navController, homeRouters)

private class ProfileGraph(navController: NavHostController) : ProfileScreens.NavGraph(navController)

private class FaqGraph(navController: NavHostController) : FqaScreens.NavGraph(navController)

private class ProjectGraph(navController: NavHostController) : ProjectScreens.NavGraph(navController)

private class SquareGraph(navController: NavHostController) : SquareScreens.NavGraph(navController)

private class SystemGraph(navController: NavHostController) : SystemScreens.NavGraph(navController)