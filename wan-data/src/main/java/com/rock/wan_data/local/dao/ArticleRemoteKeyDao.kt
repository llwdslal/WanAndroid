package com.rock.wan_data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.rock.wan_data.entity.ArticleRemoteKey


@Dao
abstract class ArticleRemoteKeyDao: BaseDao<ArticleRemoteKey>() {

    @Query("SELECT * FROM ArticleRemoteKey WHERE articleId = :articleId")
    abstract suspend fun getRemoteKeyBy(articleId:Int):ArticleRemoteKey

    @Query("DELETE FROM ArticleRemoteKey")
    abstract suspend fun clearRemoteKeys()
}