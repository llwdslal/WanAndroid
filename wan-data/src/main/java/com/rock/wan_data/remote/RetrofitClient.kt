package com.rock.wan_data.remote

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rock.wan_data.remote.intercepter.LogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

private const val TAG = "RetrofitClient"

object RetrofitClient {
    const val ExBaseUrlHeaderName = "ExBaseUrl"

    private var logEnable = false
    private var mBaseUrl: String? = null
    private var isInit = false

    private val interceptors = ArrayList<Interceptor>()
    private val retrofit: Retrofit by lazy { buildRetrofit() }

    fun init(baseUrl: String) {
        mBaseUrl = baseUrl
        isInit = true
    }

    fun logEnable(enable: Boolean): RetrofitClient {
        logEnable = enable
        return this
    }

    fun addInterceptor(interceptor: Interceptor): RetrofitClient {
        if (isInit) {
            Log.w(TAG, "RetrofitClient already init, please add Interceptor before init")
            return this
        }

        interceptors.add(interceptor)
        return this
    }

    fun addInterceptors(interceptors: List<Interceptor>): RetrofitClient {
        if (isInit) {
            Log.w(TAG, "RetrofitClient already init, please add Interceptor before init")
            return this
        }

        if (interceptors.isNotEmpty()) {
            RetrofitClient.interceptors.addAll(interceptors)
        }

        return this
    }


    fun <T> createService(service: Class<T>): T = retrofit.create(service)

    private fun buildRetrofit(): Retrofit {
        if (mBaseUrl == null || mBaseUrl!!.isEmpty()) {
            throw RuntimeException("RetrofitClient must init() with baseUrl")
        }

        val okHttpClient = generateOkHttpClient()
        val baseUrl = mBaseUrl!!
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .callFactory(MultiBaseUrlCallFactory(baseUrl, ExBaseUrlHeaderName, okHttpClient))
            .build()
    }

    private fun generateOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (logEnable) {
            interceptors.add(LogInterceptor())
        }
        interceptors.forEach { builder.addInterceptor(it) }
        return builder.build()
    }

}