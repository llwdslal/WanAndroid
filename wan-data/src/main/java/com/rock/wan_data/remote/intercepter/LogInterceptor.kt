package com.rock.wan_data.remote.intercepter

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "LogInterceptor"
class LogInterceptor : Interceptor{
    private val gson = Gson()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.e(TAG, "intercept: ${request.url} ${System.currentTimeMillis()}" )
        val response  = chain.proceed(request)
        Log.e(TAG, "intercept: ${request.url} ${System.currentTimeMillis()} \n responseCode:${response.code} \n response: ${response.peekBody(1024*1024).string()}")
        return response
    }
}