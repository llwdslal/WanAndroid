package com.rock.ui_discovery

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.items
import com.rock.lib_base.ktx.fromHtml
import com.rock.lib_compose.widget.ArticleCard
import com.rock.lib_compose.widget.PageScaffold
import com.rock.lib_compose.widget.RefreshLazyColumn

private const val TAG = "UiDiscovery"

private val tabTitles = arrayOf("广场", "问答")

@Composable
fun UiDiscovery(navController: NavController, viewModel: DiscoveryViewModel) {
    val discoveryState =
        rememberUiDiscoveryState(navController = navController, viewModel = viewModel)

    PageScaffold(
        statusBarColor = Color.Red,
    ) {
        Column(
            modifier = Modifier.padding(top = 1.dp).fillMaxSize()
        ) {
            TabRow(selectedTabIndex = discoveryState.selectedTabIndex.value) {
                tabTitles.forEachIndexed { index, tabTitle ->
                    Tab(
                        text = { Text(text = tabTitle) },
                        selected = discoveryState.selectedTabIndex.value == index,
                        onClick = { discoveryState.dispatchAction(DiscoveryAction.SelectTab(index)) }
                    )
                }
            }

            RefreshLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                state = discoveryState.currentListState,
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                onRefresh = { discoveryState.dispatchAction(DiscoveryAction.RefreshList) },
                isRefreshing = discoveryState.currentPagingItems.loadState.refresh == LoadState.Loading,
                shouldShowLoadingState = { discoveryState.currentPagingItems.loadState.append == LoadState.Loading }
            ) {

                items(discoveryState.currentPagingItems, key = { it.id }) { article ->
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

