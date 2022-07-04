package com.rock.wan_data.local.converters

import androidx.room.TypeConverter
import com.rock.wan_data.entity.Tag
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

object TagListConverter: BaseTypeConverter<List<Tag>>() {
    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    override fun fromJson(json: String): List<Tag> = gson.fromJson(json, typeOf<List<Tag>>().javaType)
}

