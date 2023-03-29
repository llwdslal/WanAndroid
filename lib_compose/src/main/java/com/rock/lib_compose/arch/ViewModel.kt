package com.rock.lib_compose.arch

import androidx.lifecycle.viewModelScope
import com.rock.lib_base.arch.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class ComposeViewModel:BaseViewModel(){

    fun collectState( block:suspend (CoroutineScope)->Unit){
        viewModelScope.launch {
            block(this)
        }
    }
}