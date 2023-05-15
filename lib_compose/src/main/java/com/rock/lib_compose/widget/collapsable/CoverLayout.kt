package com.rock.lib_compose.widget.collapsable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import com.rock.lib_compose.widget.collapsable.CollapsableLayoutState
import com.rock.lib_compose.widget.collapsable.CollapsableScrollConnection
import com.rock.lib_compose.widget.collapsable.rememberCollapsableLayoutState


@Composable
fun CoverLayout(
    topContent: @Composable BoxScope.()-> Unit,
    bottomContent: @Composable BoxScope.() -> Unit,
    bottomContentScrolled: State<Boolean> = mutableStateOf(false),
    state: CollapsableLayoutState = rememberCollapsableLayoutState()
) {

    val connection: CollapsableScrollConnection = remember {
        CollapsableScrollConnection(bottomContentScrolled, state)
    }

    Layout(modifier = Modifier
        .nestedScroll(connection), content = {
        Box(content = topContent )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .background(Color.Green),
            content = bottomContent
        )
    }) { measurables, constraints ->
        val placeableTop = measurables[0].measure(constraints)
        if (state.maxTopHeightPx == -1){
            state.updateMaxTopHeight(placeableTop.height)
        }
        val topHeight = state.calcTopHeight()
        //bottom 的最大高度约束要减去 top 容器的高度
        val bottomConstraints =
            constraints.copy(maxHeight = constraints.maxHeight -  topHeight)

        val placeableBottom = measurables[1].measure(bottomConstraints)

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeableTop.placeRelative(0, 0)
            //bottom 容器放在 top 之下
            placeableBottom.placeRelative(0, topHeight)
        }
    }
}


