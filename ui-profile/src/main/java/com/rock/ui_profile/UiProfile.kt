package com.rock.ui_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rock.lib_compose.arch.NavigationAction
import com.rock.lib_compose.theme.WanAndroidTheme
import com.rock.ui_profile.route.ProfileScreens

@Composable
fun UiProfile(navController: NavController,viewModel: ProfileViewModel = hiltViewModel()) {
    val profileState = rememberUiProfileState(viewModel = viewModel, navController = navController)
    if (profileState.user == null){
        UnLoginView {
            profileState.dispatchNavAction(NavigationAction.Nav(ProfileScreens.Login.createRoute()))
        }
    }else{
        ProfileView(profileState)
    }
}


@Composable
private fun UnLoginView(navToLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(
            modifier = Modifier
                .border(2.dp, Color.Gray, CircleShape)
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_profile_unlogin),
            contentDescription = ""
        )
        OutlinedButton(onClick = navToLogin) {
            Text(text = "登录",fontSize = 18.sp,)
        }
    }
}

@Composable
private fun ProfileView(state:UiProfileState){
    Column(modifier = Modifier.fillMaxSize(),) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(WanAndroidTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val tintColor = WanAndroidTheme.colorScheme.secondary

            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                tint = tintColor,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = state.user!!.nickname,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = tintColor
            )
        }

        ProfileMenuItem(title = "我的收藏") {

        }
        Divider()
        ProfileMenuItem(title = "我的分享") {}
        Divider()
        ProfileMenuItem(title = "我的积分") {}

        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.padding(bottom = 18.dp,start = 30.dp, end = 30.dp).fillMaxWidth(),
            onClick = { state.dispatchAction(ProfileAction.Logout) }) {
            Text(modifier= Modifier.padding(vertical = 8.dp),text = "退出登录",fontSize = 18.sp)
        }
    }
}

@Composable
fun ProfileMenuItem(title: String, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        headlineContent = { Text(text = title, style = WanAndroidTheme.typography.titleMedium) },
        trailingContent = { Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "") }
    )
}