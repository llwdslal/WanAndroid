package com.rock.lib_compose.arch

import android.content.res.Resources
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import com.rock.lib_base.arch.*
import com.rock.lib_compose.navigation.NavInterceptor
import com.rock.lib_compose.navigation.NotFoundInterceptor
import com.rock.lib_compose.navigation.navigate
import kotlinx.coroutines.CoroutineScope

abstract class ComposeState {
    abstract val navController: NavController
    abstract val resources: Resources  // compose 中获取 res
    abstract val coroutineScope: CoroutineScope // compose 的 CoroutineScope

    fun navigate(
        route: String,
        interceptors: List<NavInterceptor> = listOf(NotFoundInterceptor),
        builder: (NavOptionsBuilder.() -> Unit)
    ) {
        navController.navigate(
            route,
            interceptors,
            builder
        )
    }
}

abstract class ComposeVmState<A: IAction>: ComposeState(), ActionConsumer<A> {
    abstract val dataActionConsumer: ActionConsumer<A>
    abstract val isLoading: Boolean

    fun dispatchAction(action:A){
        when(action){
            is UiAction -> this.onAction(action)
            is DataAction -> dataActionConsumer.onAction(action)
            else -> {
                this.onAction(action)
                dataActionConsumer.onAction(action)
            }
        }
    }

    fun dispatchNavAction(action: NavigationAction){
        when(action){
            is NavigationAction.Nav -> navigate(route = action.route, builder = action.builder)
            is NavigationAction.Back -> navController.navigateUp()
        }
    }
}

@Composable
fun commonState(vm: BaseViewModel<*>):Triple<Boolean,Resources,CoroutineScope>{
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