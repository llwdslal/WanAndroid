package com.rock.ui_profile.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.BaseViewModel
import com.rock.lib_base.arch.collectStatus
import com.rock.wan_domain.interactor.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
): BaseViewModel<LoginAction>() {

    private var loginState:MutableState<Boolean> = mutableStateOf(false)

    val isLoginSuccess:Boolean
        get() = loginState.value

    override fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.Login -> doLogin(action)
            else ->{}
        }
    }

    private fun doLogin(action:LoginAction.Login) {
        viewModelScope.launch {
            loginInteractor(LoginInteractor.Params(action.username,action.password))
                .collectStatus(invokeCounter, successHandler = {
                    action.callback?.invoke()
                } )
        }
    }

}