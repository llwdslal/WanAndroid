package com.rock.ui_profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rock.lib_compose.theme.WanAndroidTheme

@Composable
fun UiProfile() {
    Text(
        modifier = Modifier.padding(horizontal = WanAndroidTheme.spacing.largePadding), text = "我的"
    )
}