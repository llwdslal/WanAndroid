package com.rock.lib_compose.widget.collapsable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.rock.lib_compose.widget.collapsable.CollapsableLayoutState
import com.rock.lib_compose.widget.collapsable.CollapsableScrollConnection
import com.rock.lib_compose.widget.collapsable.rememberCollapsableLayoutState

@Composable
fun CollapsableLayout(
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
    bottomContentScrolled: State<Boolean> = mutableStateOf(false),
    state: CollapsableLayoutState = rememberCollapsableLayoutState(0.dp)
) {

    val connection: CollapsableScrollConnection = remember {
        CollapsableScrollConnection(bottomContentScrolled, state)
    }

    val heightModifier = if (state.maxTopHeightPx != -1) {
        Modifier.height(with(LocalDensity.current){
            state.calcTopHeight().toDp()
        })
    } else {
        Modifier
    }

    Column(modifier = Modifier
        .nestedScroll(connection)
    ) {
        Box(
            modifier = Modifier.then(heightModifier)
                .onSizeChanged {
                    if (state.maxTopHeightPx == -1) {
                        state.updateMaxTopHeight(it.height)
                    }
                }
        ) { topContent() }

        Box(
            modifier = Modifier.fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) { bottomContent() }
    }
}





