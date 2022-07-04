package com.rock.wan_data.remote

import okhttp3.Call
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

/**
 * 支持多个baseUrl , Retrofit 只支持单一 baseUrl
 * 其他的 baseUrl 通过在 添加 header  ExBaseUrl 实现
 * @property baseUrl String
 * @property delegate Factory
 * @constructor
 */
class MultiBaseUrlCallFactory(
    private val baseUrl: String,
    private val headerName: String,
    private val delegate: Call.Factory
) : Call.Factory by delegate {
    override fun newCall(request: Request): Call {
        val exBaseUrl = request.header(headerName)
        exBaseUrl?.let {
            if (baseUrl != it) {
                val requestUrl = request.url.toString()
                val newUrlStr = requestUrl.replace(baseUrl, exBaseUrl)
                val newUrl = newUrlStr.toHttpUrl()
                val newRequest = request.newBuilder().url(newUrl).build()
                return delegate.newCall(newRequest)
            }
        }
        return delegate.newCall(request)
    }
}