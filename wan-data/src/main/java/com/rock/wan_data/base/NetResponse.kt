package com.rock.wan_data.base

data class ResponseException(val code:Int,val msg:String):Exception()

object ResponseCode {
    val OK = 0
}

data class NetResponse<T>(val data:T,val errorCode:Int,val errorMsg:String)

suspend fun <T,R> NetResponse<T>.handleResponse(handler:suspend (T)->R):R{
    if (errorCode == ResponseCode.OK){
        return handler(this.data)
    }else{
        //todo 统一处理 errorCode
        throw ResponseException(errorCode,errorMsg)
    }
}