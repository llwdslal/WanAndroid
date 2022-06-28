package com.rock.wan_data.base


object ResponseCode {
    val OK = 0
}

data class NetResponse<T>(val data:T,val errorCode:Int,val errorMsg:String)

suspend fun <T,R> NetResponse<T>.handleResponse(handler:suspend (T)->R):R{
    if (errorCode == ResponseCode.OK){
        return handler(this.data)
    }else{
        throw Throwable()
    }
}