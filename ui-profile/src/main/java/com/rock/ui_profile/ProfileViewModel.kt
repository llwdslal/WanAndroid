package com.rock.ui_profile

import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.BaseViewModel
import com.rock.lib_base.arch.collectStatus
import com.rock.lib_compose.arch.ComposeViewModel
import com.rock.wan_domain.interactor.LogoutInteractor
import com.rock.wan_domain.observer.UserObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userObserver: UserObserver,
    private val logoutInteractor: LogoutInteractor
):ComposeViewModel() {
    init {
        userObserver(Unit)
    }

    val userFlow = userObserver.flow


    fun logout(){
        viewModelScope.launch {
            logoutInteractor(Unit).collectStatus(invokeCounter)
        }
    }

}