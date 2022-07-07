package com.rock.lib_base.arch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

//参考 https://github.com/chrisbanes/tivi.git

typealias InvokeSuccessHandler<T> = (T?)->Unit
typealias InvokeErrorHandler<T> = (error:InvokeStatus.Error<T>)->Unit

sealed class InvokeStatus<T> {
    class Start<T> : InvokeStatus<T>()
    class Success<T>(val result: T?) : InvokeStatus<T>()
    class Error<T>(val message: String,val data: T? = null,val code: Int? = null) : InvokeStatus<T>()
}

class InvokeCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val flow: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addCounter() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeCounter() {
        loadingState.value = count.decrementAndGet()
    }
}


suspend fun <T> Flow<InvokeStatus<T>>.collectStatus(
    invokeCounter:InvokeCounter,
    successHandler:InvokeSuccessHandler<T>? = null,
    errorHandler: InvokeErrorHandler<T>? = null,
) {
    this.collect { status ->
        when(status){
            is InvokeStatus.Start -> invokeCounter.addCounter()
            is InvokeStatus.Success -> {
                invokeCounter.removeCounter()
                successHandler?.invoke(status.result)
            }
            is InvokeStatus.Error ->{
                invokeCounter.removeCounter()
                errorHandler?.invoke(status)
            }
        }
    }
}