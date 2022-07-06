package com.rock.ui_home

import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.BaseViewModel
import com.rock.lib_base.arch.collectStatus
import com.rock.wan_domain.interactor.BannerInteractor
import com.rock.wan_domain.observer.BannerObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bannerInteractor: BannerInteractor,
    val bannerObserver: BannerObserver
) :BaseViewModel<HomeAction>() {


    init {
        bannerObserver(Unit)
        refresh()
    }


    override fun onAction(action: HomeAction) {
        when (action){
            is HomeAction.Refresh -> refresh()
        }
    }

    private fun refresh(){
        viewModelScope.launch {
            bannerInteractor(Unit).collectStatus(invokeCounter)
        }
    }

}