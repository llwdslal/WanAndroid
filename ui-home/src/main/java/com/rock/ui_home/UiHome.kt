package com.rock.ui_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.items
import com.rock.lib_base.ktx.fromHtml
import com.rock.lib_compose.widget.ArticleCard
import com.rock.lib_compose.widget.Banner
import com.rock.lib_compose.widget.ImageBannerItem
import com.rock.lib_compose.widget.RefreshLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiHome(navController: NavController,viewModel: HomeViewModel = hiltViewModel()){
    LocalLifecycleOwner.current
    val homeState = rememberHomeState(viewModel = viewModel, navController = navController)

    Scaffold (
        floatingActionButton = {
            if (homeState.shouldShowToTopButton){
                FloatingActionButton(onClick = {homeState.dispatchAction(HomeAction.ToListTop)}) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "" )
                }
            }
        }
            ){ paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            Banner(items = homeState.banners) {
                ImageBannerItem(modifier = Modifier.height(200.dp),imageUrl = it.imagePath)
            }
            RefreshLazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 4.dp),
                state = homeState.lazyListState,
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                onRefresh = { homeState.dispatchAction(HomeAction.RefreshList)},
                isRefreshing = homeState.pagedArticle.loadState.refresh == LoadState.Loading,
                shouldShowLoadingState = { homeState.pagedArticle.loadState.append == LoadState.Loading }
            ){
                //置顶文章
                items(homeState.topics, key = { it.id }) { topic ->
                    val title = topic.title.fromHtml()
                    ArticleCard(
                        title = title,
                        author = topic.author,
                        date = topic.niceDate,
                        onCollectedClick = {homeState.dispatchAction(HomeAction.CollectTopic(topic.id))},
                        onClick = {}
                    )
                }
                //分页文章
                items(homeState.pagedArticle, key = {it.id}) { article ->
                    val title = article?.title?.fromHtml()
                    ArticleCard(
                        title = title,
                        author = article?.author,
                        date = article?.niceDate,
                        showPlaceHolder = article == null,
                        onCollectedClick = {},
                        onClick = {}
                    )
                }
            }
        }
    }
}