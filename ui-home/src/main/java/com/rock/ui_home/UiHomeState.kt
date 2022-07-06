package com.rock.ui_home

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.rock.lib_base.arch.ActionConsumer
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState
import com.rock.wan_data.entity.Banner
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberHomeState(viewModel: HomeViewModel,navController: NavController):HomeState{
    val banners by viewModel.bannerObserver.flow.collectAsState(initial = emptyList())

    val (isLoading,resources,coroutineScope) = commonState(vm = viewModel)

    return remember(
        banners,isLoading,coroutineScope,viewModel,navController
    ){
        HomeState(
            banners = banners,
            isLoading = isLoading,
            dataActionConsumer = viewModel,
            navController = navController,
            resources = resources,
            coroutineScope = coroutineScope
        )
    }
}


class HomeState(
    val banners:List<Banner>,
    override val isLoading: Boolean,
    override val dataActionConsumer: ActionConsumer<HomeAction>,
    override val navController: NavController,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
):ComposeVmState<HomeAction>(){

    override fun onAction(action: HomeAction) {

    }
}

