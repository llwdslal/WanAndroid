package com.rock.lib_compose.arch

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import com.rock.lib_compose.navigation.NavInterceptor
import com.rock.lib_compose.navigation.NotFoundInterceptor
import com.rock.lib_compose.navigation.navigate
import kotlinx.coroutines.CoroutineScope

abstract class ComposeState {
    abstract val navController: NavHostController
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
//获取 compose 中的 res
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}