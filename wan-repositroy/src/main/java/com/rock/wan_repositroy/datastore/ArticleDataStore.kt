package com.rock.wan_repositroy.datastore

import com.rock.wan_data.entity.Article
import com.rock.wan_data.local.dao.ArticleDao
import com.rock.wan_data.local.datastore.TopicPDS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataStore @Inject constructor(
    private val articleDao: ArticleDao,
    private val topicPDS: TopicPDS
) {
    fun getTopicByIds(ids:List<Int>): List<Article> = articleDao.getTopics(ids)
    suspend fun saveTopics(articles:List<Article>) = articleDao.insertAll(articles)

    fun getTopicIds(): Flow<List<Int>> = topicPDS.getTopicIds()
    suspend fun saveTopicIds(ids:List<Int>) = topicPDS.saveTopicIds(ids)
}