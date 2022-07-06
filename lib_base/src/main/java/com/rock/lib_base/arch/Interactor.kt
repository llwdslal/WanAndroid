package com.rock.lib_base.arch

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

//参考 https://github.com/chrisbanes/tivi.git

abstract class Interactor<in P,R> {
    operator fun invoke(
        params: P,
        timeoutMs: Long = defaultTimeoutMs,
    ): Flow<InvokeStatus<R>> = flow {
        try {
            withTimeout(timeoutMs) {
                emit(InvokeStatus.Start())
                val result:R = doWork(params)
                emit(InvokeStatus.Success(result))
            }
        } catch (t: TimeoutCancellationException) {
            val msg = t.message ?: "TimeoutCancellationException"
            emit(InvokeStatus.Error(message = msg))
        }
    }.catch { t ->
        val msg = t.message ?: "Interactor Invoke Error"
        emit(InvokeStatus.Error(message = msg))
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P):R

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

abstract class SubjectInteractor<P : Any, T> {
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow: Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest {
            createObservable(it)
        }
        .distinctUntilChanged()

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract fun createObservable(params: P): Flow<T>
}
