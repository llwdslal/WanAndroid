package com.rock.lib_base.arch

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<A:IAction>: ViewModel(),ActionConsumer<A>{
    open val invokeCounter = InvokeCounter()
}