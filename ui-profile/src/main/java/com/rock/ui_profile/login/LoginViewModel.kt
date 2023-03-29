package com.rock.ui_profile.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.collectStatus
import com.rock.lib_compose.arch.ComposeViewModel
import com.rock.wan_domain.interactor.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
): ComposeViewModel() {

    private var loginState:MutableState<Boolean> = mutableStateOf(false)

    val isLoginSuccess:Boolean
        get() = loginState.value

    fun doLogin(uname:String,pwd:String,onSuccess:()->Unit) {
        viewModelScope.launch {
            loginInteractor(LoginInteractor.Params(uname,pwd))
                .collectStatus(invokeCounter, successHandler = {
                    onSuccess()
                } )
        }
    }

}