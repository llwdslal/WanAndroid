package com.rock.ui_profile

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState
import com.rock.wan_data.entity.User
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberUiProfileState(viewModel: ProfileViewModel,navController: NavController):UiProfileState{
    val user by viewModel.userFlow.collectAsState(initial = null)

    val (isNetLoading,resources,coroutineScope) = commonState(viewModel)

    return remember(user,navController,viewModel,isNetLoading,resources,coroutineScope) {
        UiProfileState(
            user = user,
            navController = navController,
            viewModel = viewModel,
            isLoading = isNetLoading,
            resources = resources,
            coroutineScope = coroutineScope
        )
    }
}

class UiProfileState(
    val user: User?,
    override val navController: NavController,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope,
    override val viewModel: ProfileViewModel,
    override val isLoading: Boolean
) :ComposeVmState<ProfileAction,ProfileViewModel>(){

    override fun dispatchAction(action: ProfileAction) {
        when(action){
            ProfileAction.Logout -> viewModel.logout()
        }
    }

}