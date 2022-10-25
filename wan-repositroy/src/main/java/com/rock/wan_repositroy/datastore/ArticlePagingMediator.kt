package com.rock.wan_repositroy.datastore

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.ArticleRemoteKey
import com.rock.wan_data.local.WanAndroidDB
import com.rock.wan_data.local.dao.ArticleDao
import com.rock.wan_data.local.dao.ArticleRemoteKeyDao
import com.rock.wan_data.remote.WanAndroidService
import com.rock.wan_data.remote.handleResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

private const val TAG = "ArticleRemoteMediator"

@OptIn(ExperimentalPagingApi::class)
@ViewModelScoped
class ArticlePagingMediator @Inject constructor(
    private val articleDao: ArticleDao,
    private val db: WanAndroidDB,
    private val remoteKeyDao: ArticleRemoteKeyDao,
    private val articleService: WanAndroidService,
): RemoteMediator<Int, Article>() {

    val pagingSource
        get() = articleDao.pagingSource()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {//刷新
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                val page = remoteKey?.nextKey?.minus(1) ?: 0
                Log.e(TAG, "load: LoadType.REFRESH:$page", )
                page
            }
            LoadType.PREPEND -> {//向前加载
                val remoteKey = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                Log.e(TAG, "load: LoadType.PREPEND:$prevKey", )
                prevKey
            }
            LoadType.APPEND ->{//向后追加
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                Log.e(TAG, "load: LoadType.APPEND:$nextKey", )
                nextKey
            }
        }


        try {
            val pagedArticles = articleService.getArticle(page,state.config.pageSize).handleResponse { it }
            val articles = pagedArticles.datas
            val endOfPaginationReached = pagedArticles.over

            db.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeyDao.clearRemoteKeys()
                    articleDao.clearArticle()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.e(TAG, "load:withTransaction  prevKey=$prevKey <> next = $nextKey", )
                val keys = articles.map { article ->  ArticleRemoteKey(article.id,prevKey,nextKey)}
                remoteKeyDao.insertAll(keys)
                articleDao.insertAll(articles)
            }

            return MediatorResult.Success(endOfPaginationReached)
        }catch (e:Exception){
            if (e is CancellationException){
                throw e
            }
            return MediatorResult.Error(e)
        }
    }

    /*
    返回 state.anchorPosition(所显示列表中的第一个可见位置)对应的 RemoteKey
    首次加载数据时返回 null
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Article>):ArticleRemoteKey?{
        return  state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                remoteKeyDao.getRemoteKeyBy(articleId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>):ArticleRemoteKey?{

        val lastPage = state.pages.lastOrNull()
        val lastArticle = lastPage?.data?.lastOrNull()
        Log.e(TAG, "getRemoteKeyForLastItem: ${lastArticle?.id}", )
        val lastKey = lastArticle?.let {
            remoteKeyDao.getRemoteKeyBy(it.id)
        }
        return  lastKey
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>):ArticleRemoteKey?{
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeyDao.getRemoteKeyBy(it.id)
            }
    }

    override suspend fun initialize(): InitializeAction {
        val remoteSourceChanged =  remoteSourceChanged()
        Log.e(TAG, "initialize ->remoteSourceChanged :$remoteSourceChanged")
        return  if (remoteSourceChanged) InitializeAction.LAUNCH_INITIAL_REFRESH else InitializeAction.SKIP_INITIAL_REFRESH
    }

    private suspend fun remoteSourceChanged():Boolean = withContext(Dispatchers.IO) {
        val localDeferred = async { articleDao.firstOrNullArticle() }
        val remoteDeferred = async { articleService.getArticle(0, 2).data.datas.firstOrNull() }
        val localFirstArticle =  localDeferred.await()
        val remoteFirstArticle =  remoteDeferred.await()
        localFirstArticle == null || remoteFirstArticle == null || localFirstArticle.id != remoteFirstArticle.id
    }
}