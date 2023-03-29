package com.rock.lib_base.arch

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel(){
    open val invokeCounter = InvokeCounter()
}