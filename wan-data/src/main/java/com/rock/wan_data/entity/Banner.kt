package com.rock.wan_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Banner(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val desc: String,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)