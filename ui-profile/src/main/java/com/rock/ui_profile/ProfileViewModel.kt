package com.rock.ui_profile

import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.BaseViewModel
import com.rock.lib_base.arch.collectStatus
import com.rock.wan_domain.interactor.LogoutInteractor
import com.rock.wan_domain.observer.UserObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userObserver: UserObserver,
    private val logoutInteractor: LogoutInteractor
):BaseViewModel<ProfileAction>() {
    init {
        userObserver(Unit)
    }

    val userFlow = userObserver.flow

    override fun onAction(action: ProfileAction) {
        when(action){
            ProfileAction.Logout -> logout()
        }
    }

    private fun logout(){
        viewModelScope.launch {
            logoutInteractor(Unit).collectStatus(invokeCounter)
        }
    }

}