package com.rock.lib_compose.widget.collapsable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class TopStates {
    EXPANDED,// 默认展开状态
    SCROLLING,//中间状态 ,中间状态时 connection 也要消耗掉全部事件
    COLLAPSED// 折叠状态
}

@Composable
fun rememberCollapsableLayoutState(minTopHeightDp: Dp = 0.dp): CollapsableLayoutState {
    val density = LocalDensity.current
    return remember(minTopHeightDp,density) {
        CollapsableLayoutState(minTopHeightDp, density)
    }
}

class CollapsableLayoutState(
    private val minTopHeightDp: Dp,
    density: Density
){
    private val offsetState: MutableState<Offset> = mutableStateOf(Offset.Zero)
    private val topState: MutableState<TopStates> = mutableStateOf(TopStates.EXPANDED)
    //top 最大高度需要 top 容器经过测量后得到
    private val maxHeightState: MutableState<Int> = mutableStateOf(-1)
    //默认 EXPANDED 状态 ，progress 默认为 1
    private val expendProgressState = mutableStateOf(1f)

    val currentTopState
        get() = topState.value

    val minTopHeightPx = with(density) {
        minTopHeightDp.toPx().toInt()
    }

    val maxTopHeightPx
        get() = maxHeightState.value

    val maxOffsetY
        get() = maxTopHeightPx - minTopHeightPx

    val expendProgress
        get() = expendProgressState.value

    fun shouldConsumeAvailable(available: Offset): Boolean {
        return topState.value == TopStates.SCROLLING //处于折叠和展开状态
                //展开状态 向上滑动
                || topState.value == TopStates.EXPANDED && available.y < 0
                //折叠状态 向下滑动
                || topState.value == TopStates.COLLAPSED && available.y > 0
    }

    fun plusOffset(offset: Offset) {
        offsetState.value = offsetState.value + offset
    }

    /**
     * 设置 top 容器最大高度
     * @param maxHeight Int
     */
    fun updateMaxTopHeight(maxHeight: Int) {
        if (maxHeight == maxHeightState.value) return
        maxHeightState.value = maxHeight
    }

    /*
       根据 offsetState 计算当前 top 容器的高度
     */
    fun calcTopHeight():Int{
        val curTopHeight = (maxTopHeightPx + offsetState.value.y.toInt()).coerceIn(minTopHeightPx,maxTopHeightPx)
        //根据 curTopHeight 设置 LayoutState ，计算 expendProgressState
        when (curTopHeight) {
            minTopHeightPx -> {
                offsetState.value = Offset(0f, -maxOffsetY.toFloat())
                expendProgressState.value = 0f
                updateLayoutState(TopStates.COLLAPSED)
            }
            maxTopHeightPx -> {
                offsetState.value = Offset.Zero
                expendProgressState.value = 1f
                updateLayoutState(TopStates.EXPANDED)
            }
            else -> {
                val offsetY = (maxTopHeightPx - curTopHeight).toFloat()
                expendProgressState.value = 1 - offsetY / maxOffsetY
                updateLayoutState(TopStates.SCROLLING)
            }
        }
        return curTopHeight
    }

    private fun updateLayoutState(state: TopStates){
        if (state == topState.value) return
        topState.value = state
    }
}

class CollapsableScrollConnection(
    private val isChildScrolled: State<Boolean> = mutableStateOf(false),
    private val state: CollapsableLayoutState
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (isChildScrolled.value) return Offset.Zero
        if (state.shouldConsumeAvailable(available)) {
            state.plusOffset(available)
            return available
        }
        return Offset.Zero
    }
}