package com.rock.wan_data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

abstract class BaseTypeConverter<T> {
    companion object{
        val gson = Gson()
    }

    abstract fun fromJson(json: String): T // 将 json 字符串转换成对象

    @TypeConverter
    fun toJson(obj:T): String = gson.toJson(obj) //将对象转换成 json 字符串
}