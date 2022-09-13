package com.rock.ui_fqa

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rock.lib_compose.theme.WanAndroidTheme

@Composable
fun UiFqa(){
    Text(
        modifier = Modifier.padding(horizontal = WanAndroidTheme.spacing.largePadding), text = "问答"
    )
}