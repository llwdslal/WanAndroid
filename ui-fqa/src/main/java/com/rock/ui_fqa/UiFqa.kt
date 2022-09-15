package com.rock.ui_fqa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rock.lib_compose.theme.LocalAutoWindowInfo
import com.rock.lib_compose.theme.WanAndroidTheme

@Composable
fun UiFqa() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.height(60.dp)
            .width(LocalAutoWindowInfo.current.screenWidthDp)
            .background(color = Color.Magenta)
        ){
            Text(text = "${LocalAutoWindowInfo.current.screenWidthDp}")
        }
        Text(
            modifier = Modifier.padding(horizontal = WanAndroidTheme.spacing.largePadding),
            text = "问答"
        )
    }
}