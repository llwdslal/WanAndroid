package com.rock.ui_discovery

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState
import com.rock.lib_compose.widget.LazyListFirstItemInfo
import com.rock.wan_data.entity.Article
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberUiDiscoveryState(
    navController: NavController,
    viewModel: DiscoveryViewModel
): UiDiscoveryState {

    val selectedTabIndex = viewModel.selectedTabIndexState
    val pagedWenda = viewModel.wendaPagingDataFlow.collectAsLazyPagingItems()
    val pagedSquare = viewModel.squarePagingDataFlow.collectAsLazyPagingItems()
    val wendaListState = rememberLazyListState(viewModel.wendaFirstItemInfo.index,viewModel.wendaFirstItemInfo.scrollOffset)
    val squareListState = rememberLazyListState(viewModel.squareFirstItemInfo.index,viewModel.squareFirstItemInfo.scrollOffset)

    val (isLoading, resources, coroutineScope) = commonState(vm = viewModel)
    return remember(viewModel) {
        UiDiscoveryState(
            selectedTabIndex = selectedTabIndex,
            pagedWenda = pagedWenda,
            pagedSquare = pagedSquare,
            wendaListState = wendaListState,
            squareListState = squareListState,
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
    val selectedTabIndex: State<Int>,
    private val pagedWenda: LazyPagingItems<Article>,
    private val pagedSquare: LazyPagingItems<Article>,
    private val wendaListState: LazyListState,
    private val squareListState: LazyListState,
    override val navController: NavController,
    override val viewModel: DiscoveryViewModel,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
    override val isLoading: Boolean
) : ComposeVmState<DiscoveryAction, DiscoveryViewModel>(),RememberObserver {

    val currentPagingItems
        get() = if (selectedTabIndex.value == 0)pagedSquare  else  pagedWenda
    val currentListState
        get() = if (selectedTabIndex.value == 0) squareListState else wendaListState

    override fun dispatchAction(action: DiscoveryAction) {
        when(action){
            is DiscoveryAction.SelectTab -> selectTable(action.index)
            DiscoveryAction.RefreshList -> currentPagingItems.refresh()
        }
        wendaListState.firstVisibleItemScrollOffset
    }

    private fun selectTable(tabIndex: Int) {
        Log.e(TAG, "selectTable: $tabIndex" )
        viewModel.selectedTabIndexState.value = tabIndex
    }

    override fun onAbandoned() {
    }

    override fun onForgotten() {
        viewModel.wendaFirstItemInfo = LazyListFirstItemInfo(wendaListState.firstVisibleItemIndex,wendaListState.firstVisibleItemScrollOffset)
        viewModel.squareFirstItemInfo = LazyListFirstItemInfo(squareListState.firstVisibleItemIndex,squareListState.firstVisibleItemScrollOffset)
    }

    override fun onRemembered() {
    }

}