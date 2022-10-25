package com.rock.lib_compose.widget

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> Banner(
    modifier: Modifier = Modifier,
    items: List<T>,
    onItemClick: ((T) -> Unit)? = null,
    autoLoop:Boolean = true,
    loopInterval:Long = 3000L,
    activeIndicatorColor: Color = Color.Magenta,
    inactiveIndicatorColor: Color = Color.Gray,
    itemContent: @Composable (T) -> Unit
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    var isAutoLoop by remember { mutableStateOf(autoLoop) }

    //手动滚动时取消自动翻页
    LaunchedEffect(pagerState.interactionSource) {
        pagerState.interactionSource.interactions.collect {
            isAutoLoop = when (it) {
                is DragInteraction.Start -> false
                else -> true
            }
        }
    }


    if(items.isNotEmpty()){
        // 重组时 pagerState.currentPage 发生变化就会重新执行
        LaunchedEffect(pagerState.currentPage,isAutoLoop){
            if (isAutoLoop){
                delay(loopInterval)
                val nextPageIndex = (pagerState.currentPage + 1) % items.size
                scope.launch {
                    // animateScrollToPage
                    pagerState.animateScrollToPage(nextPageIndex)
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        count = items.size,
        modifier = Modifier
            //！！！！ 初始组合时 lambda 中 items 是其默认值是 empty，所以要以 item 为 key
            .pointerInput(items) {
                detectTapGestures(
                    onPress = {
                        isAutoLoop = false
                        val pressStartTime = System.currentTimeMillis()
                        //只监听 release
                        if (tryAwaitRelease()) {
                            isAutoLoop = true
                            val pressDuration = System.currentTimeMillis() - pressStartTime
                            //长按后 release 不触发 onClick 事件
                            if (pressDuration < viewConfiguration.longPressTimeoutMillis) { //400 ms
                                onItemClick?.invoke(items[pagerState.currentPage])
                            }
                        }
                    },
                )
            }
            .then(modifier)
    ) { pageIndex ->
        val itemData = items[pageIndex]
        Box(modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()) {
            //内容
            itemContent(itemData)
            //指示器
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                pagerState = pagerState,
                activeColor = activeIndicatorColor,
                inactiveColor = inactiveIndicatorColor
            )
        }
    }
}

@Composable
fun ImageBannerItem(imageUrl:String,modifier: Modifier = Modifier){
    var placeHolderVisible by remember { mutableStateOf(true) }
    AsyncImage(
        model = imageUrl,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .defaultPlaceHolder(placeHolderVisible)
            .then(modifier),
        onState = {
            when (it) {
                is AsyncImagePainter.State.Success -> {
                    placeHolderVisible = false
                }
                is AsyncImagePainter.State.Error -> {
                    //todo 异常处理
                    Log.e("AsyncImage", "ImageBannerItem:State.Error ${it.result.throwable} ")
                }
                else->{

                }
            }
        }
    )
}