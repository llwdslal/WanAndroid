package com.rock.lib_compose.navigation

import androidx.core.net.toUri
import androidx.navigation.*

interface NavInterceptor{
    /**
     * 是否需要拦截
     * @return Boolean true 拦截
     */
    fun shouldIntercept(
        route: String,
        navOptions: NavOptions?,
        navController: NavController
    ): Boolean

    /**
     *拦截后的导航逻辑
     */
    fun navigate(navController: NavController,originalRoute:String)
}


fun NavController.navigate(route: String, interceptors:List<NavInterceptor>? = null ,builder: NavOptionsBuilder.() -> Unit){
    val navOptions = navOptions(builder)
    //是否有需要拦截的拦截器
    val interceptor = interceptors?.find { it.shouldIntercept(route,navOptions,this) }

    if (interceptor != null){
        // 调用拦截器导航
        interceptor.navigate(navController = this, originalRoute = route)
    }else{
        navigate(route,navOptions)
    }
}

object NotFoundInterceptor:NavInterceptor{
    override fun shouldIntercept(
        route: String,
        navOptions: NavOptions?,
        navController: NavController
    ): Boolean {
        // 如果是 404 页面不拦截
        if (route == PageNotFoundScreen.route) return false

        //Compose navigate 是使用 DeepLink 匹配模式,
        //route 转换成 NavDeepLinkRequest 去 navController.graph 中匹配
        val request = NavDeepLinkRequest.Builder.fromUri(NavDestination.createRoute(route).toUri()).build()
        val requestMatch = navController.graph.matchDeepLink(request)
        return requestMatch == null
    }

    override fun navigate(navController: NavController,originalRoute:String) {
        navController.navigate(PageNotFoundScreen.route)
    }
}
// 404 页面 需要在 NavHost 参数的 NavGraphBuilder 中配置
object PageNotFoundScreen: Screen("pageNotFound"){
    override val root: String
        get() = "route404"
}

