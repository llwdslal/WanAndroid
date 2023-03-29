package com.rock.ui_profile.login

import androidx.compose.ui.text.input.TextFieldValue
import com.rock.lib_compose.arch.IAction

sealed class LoginAction: IAction {
    object ClearUsername:LoginAction()
    object TogglePwsVisible:LoginAction()
    class UsernameInputChange(val changedValue: TextFieldValue):LoginAction()
    class PasswordInputChange(val changedValue: TextFieldValue):LoginAction()
    object PwdInputFocus:LoginAction()
    data class Login(val username:String,val password:String,val callback:(() -> Unit)? = null):LoginAction()
}