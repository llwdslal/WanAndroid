package com.rock.ui_profile.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rock.lib_compose.arch.NavigationAction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val state = rememberLoginState(viewModel = viewModel, navController = navController)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = state.snackbarHostState)}
    ){
        _login(state = state,it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _login(state: UiLoginState,paddingValues: PaddingValues) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 60.dp, start = 32.dp, end = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        val usernameInputValue by state.unameInputState
        val pwdInputValue by state.pwdInputState
        val username = usernameInputValue.text.trim()
        val password = pwdInputValue.text.trim()

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(state.unameFocusRequester),
            label = { Text("用户名") },
            placeholder = { Text("请输入用户名") },
            value = usernameInputValue,
            singleLine = true,
            onValueChange = { state.dispatchAction(LoginAction.UsernameInputChange(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {state.dispatchAction(LoginAction.PwdInputFocus)}),
            trailingIcon = {
                if (usernameInputValue.text.isNotEmpty()) {
                    IconButton(onClick = { state.dispatchAction(LoginAction.ClearUsername) }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(state.pwdFocusRequester)
                .onFocusChanged {
                    if (it.isFocused && pwdInputValue.text.isNotEmpty()) {
                        val newValue =
                            pwdInputValue.copy(selection = TextRange(pwdInputValue.text.length))
                        state.dispatchAction(LoginAction.PasswordInputChange(newValue))
                    }
                },
            label = { Text("密码") },
            placeholder = { Text("请输入密码") },
            value = pwdInputValue,
            singleLine = true,
            onValueChange = { state.dispatchAction(LoginAction.PasswordInputChange(it)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {state.dispatchAction(LoginAction.Login(username,password))})
        )

        Spacer(modifier = Modifier.height(48.dp))


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                state.dispatchAction(LoginAction.Login(username, password) {
                    state.dispatchNavAction(NavigationAction.Back)
                })
            }
        ) {
            Text(text = "登录", fontSize = 18.sp, modifier = Modifier.padding(vertical = 6.dp))
        }
    }
}