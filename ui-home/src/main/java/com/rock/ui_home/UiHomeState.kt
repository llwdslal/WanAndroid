package com.rock.ui_home

import android.content.res.Resources
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rock.lib_base.arch.ActionConsumer
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState
import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.Banner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberHomeState(viewModel: HomeViewModel,navController: NavController):HomeState{
    val banners by viewModel.bannerFlow.collectAsState(initial = emptyList())
    val topics by viewModel.topicFlow.collectAsState(initial = emptyList())
    val pagedArticle = viewModel.articlesPagingDataFlow.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val toListTopVisibleState = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 10 } }
    val (isLoading,resources,coroutineScope) = commonState(vm = viewModel)

    return remember(
        banners,topics,pagedArticle,lazyListState,isLoading,coroutineScope,viewModel,navController
    ){
        HomeState(
            banners = banners,
            topics = topics,
            pagedArticle = pagedArticle,
            lazyListState = lazyListState,
            toListTopVisibleState = toListTopVisibleState,
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
    val topics: List<Article>,
    val pagedArticle: LazyPagingItems<Article>,
    val lazyListState: LazyListState,
    private val toListTopVisibleState: State<Boolean>,
    override val isLoading: Boolean,
    override val dataActionConsumer: ActionConsumer<HomeAction>,
    override val navController: NavController,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
):ComposeVmState<HomeAction>(){

    val shouldShowToTopButton
        get() = toListTopVisibleState.value

    override fun onAction(action: HomeAction) {
        when(action){
            is HomeAction.RefreshList -> refreshList()
            is HomeAction.ToListTop -> scrollToListTop()
        }
    }

    private fun scrollToListTop() {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(0)
        }
    }

    private fun refreshList() {
        pagedArticle.refresh()
    }
}

