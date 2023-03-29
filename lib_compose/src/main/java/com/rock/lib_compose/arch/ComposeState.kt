package com.rock.lib_compose.arch

import android.content.res.Resources
import android.os.Bundle
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import com.rock.lib_base.arch.*
import com.rock.lib_compose.navigation.*
import kotlinx.coroutines.CoroutineScope

private const val TAG = "ComposeState"

abstract class ComposeState {
    abstract val navController: NavController
    abstract val resources: Resources  // compose 中获取 res
    abstract val coroutineScope: CoroutineScope // compose 的 CoroutineScope


    fun navigate(
        route: String,
        interceptors: List<NavInterceptor> = listOf(NotFoundInterceptor),
        builder: (NavOptionsBuilder.() -> Unit) = {}
    ) {
        navController.navigate(
            route,
            interceptors,
            builder
        )
    }
}

abstract class ComposeVmState<A: IAction,VM:ComposeViewModel>: ComposeState() {
    abstract val viewModel: VM
    abstract val isLoading: Boolean

    abstract fun dispatchAction(action:A)

    fun dispatchNavAction(action: NavigationAction){
        when(action){
            is NavigationAction.Nav -> navigate(route = action.route, builder = action.builder)
            NavigationAction.Back -> navController.navigateUp()
        }
    }

  fun  navForResult(
        routeBuilder:(String)->String,
        requestCode:String,
        interceptors: List<NavInterceptor> = listOf(NotFoundInterceptor),
        builder: (NavOptionsBuilder.() -> Unit) = {},
        onResult:(Bundle,String) -> Unit
    ){
        val route = routeBuilder(requestCode)
        val resultFlow = navController.navigateForResult(route, interceptors, builder) ?: return

        viewModel.collectState {
            resultFlow.collect{
                if (it != null && it.getString(NavRequestCodeKey,null) == requestCode){
                    onResult(it, NavResultDataKey)
                    navController.clearForResult()
                }
            }
        }

    }
}

@Composable
fun commonState(vm: BaseViewModel):Triple<Boolean,Resources,CoroutineScope>{
    val isLoading by vm.invokeCounter.flow.collectAsState(initial = false)
    val resources = resources()
    val coroutineScope = rememberCoroutineScope()
    return Triple(isLoading,resources,coroutineScope)
}

//获取 compose 中的 res
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}