package com.rock.wan_data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ArticleRemoteKey(
    @PrimaryKey
    val articleId:Int,
    val prevKey :Int?,
    val nextKey :Int?
)