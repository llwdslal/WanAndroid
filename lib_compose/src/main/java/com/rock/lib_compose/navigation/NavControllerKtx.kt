package com.rock.lib_compose.navigation

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.navigation.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

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


const val NavResultKey = "NavResultKey"
const val NavRequestCodeKey = "RequestCode"
const val NavResultDataKey = "NavResultDataKey"



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



fun NavController.navigateForResult(
    route: String,
    interceptors:List<NavInterceptor>? = null,
    builder: NavOptionsBuilder.() -> Unit) :StateFlow<Bundle?>? {
    val navOptions = navOptions(builder)
    //是否有需要拦截的拦截器
    val interceptor = interceptors?.find { it.shouldIntercept(route,navOptions,this) }

    return if (interceptor != null){
        // 调用拦截器导航
        interceptor.navigate(navController = this, originalRoute = route)
        Log.i("OnNavResult", "[route:$route] navigateForResult has been Intercepted")
        null
    }else{
        val stateHandle = this.currentBackStackEntry!!.savedStateHandle
        navigate(route,navOptions)
        return stateHandle.getStateFlow<Bundle>(NavResultKey,Bundle.EMPTY)
    }
}

@Composable
fun NavController.navigateForResultCompose(
    route: String,
    interceptors:List<NavInterceptor>? = null,
    builder: NavOptionsBuilder.() -> Unit) :State<Bundle?>? = navigateForResult(route,interceptors,builder)?.collectAsState()

fun NavController.getRequestCode():String? = this.currentBackStackEntry!!.arguments?.getString(NavRequestCodeKey,null)

fun NavController.setResult(result: Bundle){
    val prevEntry = this.previousBackStackEntry
    val requestCode = this.getRequestCode()

    if (prevEntry != null && requestCode != null){
        result.putString(NavRequestCodeKey,requestCode)
        prevEntry.savedStateHandle[NavResultKey] = result
    }
}

fun NavController.clearForResult(){
    this.currentBackStackEntry!!.savedStateHandle.let {
        it.remove<Any>(NavResultKey)
        it[NavResultKey] = null
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
object PageNotFoundScreen: Screen<OutRoutes>("pageNotFound"){
    override val root: String
        get() = "route404"
}

