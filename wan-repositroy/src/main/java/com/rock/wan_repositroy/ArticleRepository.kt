package com.rock.wan_repositroy

import com.rock.wan_data.base.handleResponse
import com.rock.wan_data.entity.Article
import com.rock.wan_repositroy.datasource.ArticleDataSource
import com.rock.wan_repositroy.datastore.ArticleDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(
    private val articleDataStore: ArticleDataStore,
    private val articleDataSource: ArticleDataSource
) {
    fun observeTopic(): Flow<List<Article>> = articleDataStore.getTopicIds().map {
        articleDataStore.getTopicByIds(it)
    }.flowOn(Dispatchers.IO)

    suspend fun updateTopic() {
        withContext(Dispatchers.IO){
            articleDataSource.topicArticles().handleResponse {
                articleDataStore.saveTopics(it)
                articleDataStore.saveTopicIds(it.map { article -> article.id })
            }
        }
    }
}