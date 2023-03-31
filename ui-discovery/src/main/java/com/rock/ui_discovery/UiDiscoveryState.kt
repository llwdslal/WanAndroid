package com.rock.ui_discovery

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState
import com.rock.wan_data.entity.Article
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberUiDiscoveryState(
    navController: NavController,
    viewModel: DiscoveryViewModel
): UiDiscoveryState {

    val selectedTabIndex = viewModel.selectedTabIndexState.value
    val pagedWenda = viewModel.wendaPagingDataFlow.collectAsLazyPagingItems()
    val pagedGuangchang = viewModel.guangchangPagingDataFlow.collectAsLazyPagingItems()
    val wendaListState = rememberLazyListState()
    val guangchangListState = rememberLazyListState()

    val (isLoading, resources, coroutineScope) = commonState(vm = viewModel)
    return remember(selectedTabIndex,pagedWenda,wendaListState) {
        UiDiscoveryState(
            selectedTabIndex = selectedTabIndex,
            pagedWenda = pagedWenda,
            pagedGuangchang = pagedGuangchang,
            wendaListState = wendaListState,
            guangchangListState = guangchangListState,
            navController = navController,
            viewModel = viewModel,
            resources = resources,
            coroutineScope = coroutineScope,
            isLoading = isLoading
        )
    }
}
private const val TAG = "UiDiscoveryState"

class UiDiscoveryState(
    val selectedTabIndex:Int,
    private val pagedWenda: LazyPagingItems<Article>,
    private val pagedGuangchang: LazyPagingItems<Article>,
    private val wendaListState: LazyListState,
    private val guangchangListState: LazyListState,
    override val navController: NavController,
    override val viewModel: DiscoveryViewModel,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
    override val isLoading: Boolean
) : ComposeVmState<DiscoveryAction, DiscoveryViewModel>() {

    val currentPagingItems
        get() = if (selectedTabIndex == 0)pagedGuangchang  else  pagedWenda
    val currentListState
        get() = if (selectedTabIndex == 0) guangchangListState else wendaListState

    override fun dispatchAction(action: DiscoveryAction) {
        when(action){
            is DiscoveryAction.SelectTab -> selectTable(action.index)
            DiscoveryAction.RefreshList -> currentPagingItems.refresh()
        }
    }

    private fun selectTable(tabIndex: Int) {
        Log.e(TAG, "selectTable: $tabIndex" )
        viewModel.selectedTabIndexState.value = tabIndex
    }
}