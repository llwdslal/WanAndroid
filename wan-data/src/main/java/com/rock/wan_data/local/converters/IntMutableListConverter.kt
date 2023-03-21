package com.rock.wan_data.local.converters

import androidx.room.TypeConverter
import kotlin.reflect.javaType
import kotlin.reflect.typeOf


object IntMutableListConverter:BaseTypeConverter<MutableList<Int>>() {
    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    override fun fromJson(json: String): MutableList<Int> = gson.fromJson(json, typeOf<MutableList<Int>>().javaType)
}