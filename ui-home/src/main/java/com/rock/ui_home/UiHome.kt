package com.rock.ui_home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiHome(navController: NavController,viewModel: HomeViewModel = hiltViewModel()){
    val homeState = rememberHomeState(viewModel = viewModel, navController = navController)

    Scaffold { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            Text(text = "${homeState.banners.size}")
        }
    }
}