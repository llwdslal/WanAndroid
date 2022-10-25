package com.rock.wan_data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query

import com.rock.wan_data.entity.Article

@Dao
abstract class ArticleDao : BaseDao<Article>() {
    //置顶文章 type = 1
    @Query("SELECT * FROM Article WHERE type = 1 AND id in(:ids)")
    abstract fun getTopics(ids: List<Int>): List<Article>

    @Query("SELECT * FROM Article WHERE type = 0 ORDER BY publishTime DESC")
    abstract fun pagingSource(): PagingSource<Int, Article>

    @Query("SELECT * FROM Article WHERE type = 0 ORDER BY publishTime DESC LIMIT 1")
    abstract suspend fun firstOrNullArticle():Article?

    @Query("DELETE FROM Article WHERE type = 0")
    abstract suspend fun clearArticle()
}