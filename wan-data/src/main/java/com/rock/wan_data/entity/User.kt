package com.rock.wan_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rock.wan_data.local.converters.IntMutableListConverter

@Entity
@TypeConverters(IntMutableListConverter::class)
data class User(
    @PrimaryKey
    val id: Int,
    val admin: Boolean,
    val chapterTops: MutableList<Int>,
    val coinCount: Int,
    val collectIds: MutableList<Int>,
    val email: String,
    val icon: String,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
