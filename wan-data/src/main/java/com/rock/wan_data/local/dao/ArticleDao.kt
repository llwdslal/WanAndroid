package com.rock.wan_data.local.dao

import androidx.room.Dao
import androidx.room.Query

import com.rock.wan_data.base.BaseDao
import com.rock.wan_data.entity.Article
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao : BaseDao<Article>() {
    //置顶文章 type = 1
    @Query("SELECT * FROM Article WHERE type = 1 AND id in(:ids)")
    protected abstract fun getTopics(ids: List<Int>): Flow<List<Article>>

    fun getDistinctTopics(ids: List<Int>): Flow<List<Article>> = getTopics(ids)
}