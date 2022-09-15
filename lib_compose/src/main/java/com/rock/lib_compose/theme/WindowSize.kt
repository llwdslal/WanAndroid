package com.rock.lib_compose.theme

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


interface WindowCompatConfig {
    //小型设备设计稿宽高
    val designCompactSize: DpSize?
    //中型设备设计稿宽高
    val designMediumSize: DpSize?
    //大型设备设计稿宽高
    val designExpandSize: DpSize?

    companion object None:WindowCompatConfig{
        override val designCompactSize: DpSize?
            get() = null
        override val designMediumSize: DpSize?
            get() = null
        override val designExpandSize: DpSize?
            get() = null
    }
}



data class AutoWindowInfo(
    val density: Float, // 适配后的 density
    val screenWidthDp:Dp,// 适配后宽度 dp
    val screenHeightDp:Dp,
    val windowSizeClass: WindowSizeClass,//当前 WindowSize 级别
)

val LocalAutoWindowInfo = staticCompositionLocalOf <AutoWindowInfo> { error("No AutoWindowInfo provided") }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getAutoWindowInfo(activity: Activity, config: WindowCompatConfig): AutoWindowInfo{
    val sizeClass = calculateWindowSizeClass(activity)
    val orientation = LocalConfiguration.current.orientation
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    val screenHeight = displayMetrics.heightPixels
    var density = displayMetrics.density
    if (orientation == Configuration.ORIENTATION_PORTRAIT){
        when (sizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                config.designCompactSize?.let {
                    density = getDensity(screenWidth,it.width)
                }
            }
            WindowWidthSizeClass.Medium -> {
                config.designMediumSize?.let {
                    density = getDensity(screenWidth,it.width)
                }
            }
            WindowWidthSizeClass.Expanded -> {
                config.designExpandSize?.let {
                    density = getDensity(screenWidth,it.width)
                }
            }
            else -> {
            }
        }
    }else{
        when (sizeClass.heightSizeClass) {
            WindowHeightSizeClass.Compact -> {
                config.designCompactSize?.let {
                    density = getDensity(screenWidth,it.height)
                }
            }
            WindowHeightSizeClass.Medium -> {
                config.designMediumSize?.let {
                    density = getDensity(screenWidth,it.height)
                }
            }
            WindowHeightSizeClass.Expanded -> {
                config.designExpandSize?.let {
                    density = getDensity(screenWidth,it.height)
                }
            }
            else -> {
            }
        }
    }

    val screenWidthDp = (screenWidth / density).roundToInt().dp
    val screenHeightDp = (screenHeight / density).roundToInt().dp

    return AutoWindowInfo(density,screenWidthDp,screenHeightDp,sizeClass)
}

private fun getDensity(deviceWidth:Int,designWidth: Dp): Float = deviceWidth / designWidth.value
