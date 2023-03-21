package com.rock.ui_profile.login

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.rock.lib_base.arch.ActionConsumer
import com.rock.lib_compose.arch.ComposeVmState
import com.rock.lib_compose.arch.commonState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "UiLoginState"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberLoginState(viewModel: LoginViewModel, navController: NavController): UiLoginState {
    val snackbarHostState = SnackbarHostState()
    val isLoginSuccess = viewModel.isLoginSuccess
    val (isNetLoading, resources, coroutineScope) = commonState(viewModel)

    val unameInputState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("llwdslal"))
    }

    val pwdInputState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("leaves1314"))
    }

    val  keyboardController:SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val pwdFocusRequester = remember { FocusRequester() }
    val unameFocusRequester = remember { FocusRequester() }

    return remember(
        snackbarHostState,
        isLoginSuccess,
        unameInputState,
        pwdInputState,
        navController,
        keyboardController,
        unameFocusRequester,
        pwdFocusRequester,
        viewModel,
        isNetLoading,
        resources,
        coroutineScope
    ) {
        UiLoginState(
            snackbarHostState=  snackbarHostState,
            isLoginSuccess = isLoginSuccess,
            unameInputState = unameInputState,
            pwdInputState = pwdInputState,
            unameFocusRequester = unameFocusRequester,
            pwdFocusRequester =pwdFocusRequester,
            keyboardController = keyboardController,
            navController = navController,
            dataActionConsumer = viewModel,
            isLoading = isNetLoading,
            resources = resources,
            coroutineScope = coroutineScope
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Stable
class UiLoginState(
    val snackbarHostState: SnackbarHostState,
    val isLoginSuccess: Boolean,
    var unameInputState: MutableState<TextFieldValue>,
    var pwdInputState: MutableState<TextFieldValue>,
    val unameFocusRequester:FocusRequester,
    val pwdFocusRequester:FocusRequester,
    private val keyboardController:SoftwareKeyboardController?,
    override val navController: NavController,
    override val dataActionConsumer: ActionConsumer<LoginAction>,
    override val isLoading: Boolean,
    override val resources: Resources,
    override val coroutineScope: CoroutineScope
) : ComposeVmState<LoginAction>() {

    override fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.ClearUsername -> clearUsernameInput()
            is LoginAction.UsernameInputChange -> onUsernameInputChanged(action.changedValue)
            is LoginAction.PasswordInputChange -> onPasswordInputChanged(action.changedValue)
            is LoginAction.Login -> onLogin(action)
            LoginAction.PwdInputFocus -> onPwdInputFocus()
            else -> {}
        }
    }


    private fun navBack() {
        keyboardController?.hide()
    }

    private fun onLogin(action: LoginAction.Login): Boolean {
        val tipsMsg = when {
            action.username.isEmpty() -> "用户名不能为空"
            action.password.isEmpty() -> "密码不能为空"
            else -> ""
        }

        if (tipsMsg.isNotEmpty()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(tipsMsg)
            }
        } else {
            val newAction = action.copy(callback = {
                navBack()
            })
            dataActionConsumer.onAction(newAction)
        }
        return true
    }



    private fun onUsernameInputChanged(changedValue: TextFieldValue): Boolean {
        unameInputState.value = changedValue
        return true
    }

    private fun onPasswordInputChanged(changedValue: TextFieldValue): Boolean {
        pwdInputState.value = changedValue
        return true
    }

    private fun onPwdInputFocus(): Boolean {
        pwdFocusRequester.requestFocus()
        return true
    }

    private fun clearUsernameInput(): Boolean {
        unameInputState.value = TextFieldValue()
        unameFocusRequester.requestFocus()
        return true
    }
}