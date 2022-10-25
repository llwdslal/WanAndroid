package com.rock.lib_compose.widget

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RefreshLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    onRefresh :(()->Unit)? = null,
    isRefreshing:Boolean = false,
    shouldShowLoadingState:() -> Boolean = {false},
    content: LazyListScope.()-> Unit

) {
    val refreshState = rememberSwipeRefreshState(isRefreshing)
    SwipeRefresh(
        state = refreshState,
        onRefresh = { onRefresh?.invoke()},
        indicatorPadding = contentPadding,
        indicator = { _state, trigger ->
            SwipeRefreshIndicator(
                state = _state,
                refreshTriggerDistance = trigger,
                scale = true
            )
        }) {
        LazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
        ) {
            content()
            //底部显示加载中 UI
            if (shouldShowLoadingState()) {
                item(key = "RefreshLazyColumn_Load_Indicator") {
                    Box(Modifier.fillMaxWidth().padding(24.dp)
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}