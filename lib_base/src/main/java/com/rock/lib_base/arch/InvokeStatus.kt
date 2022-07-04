package com.rock.lib_base.arch

sealed class InvokeStatus {
    object Start : InvokeStatus()
    class Success<T>(data: T?) : InvokeStatus()
    class Error<T>(message: String, data: T? = null, code: Int? = null) : InvokeStatus()
}