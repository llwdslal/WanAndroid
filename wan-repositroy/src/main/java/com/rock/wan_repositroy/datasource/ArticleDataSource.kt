package com.rock.wan_repositroy.datasource

import com.rock.wan_data.remote.WanAndroidService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataSource @Inject constructor(
    private val service: WanAndroidService
) {
    suspend fun topicArticles() = service.getTopArticles()
}