package com.rock.wan_repositroy.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.PagedData
import com.rock.wan_data.remote.NetResponse
import com.rock.wan_data.remote.WanAndroidService
import com.rock.wan_data.remote.handleResponse
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class ArticlePagingSource  @Inject constructor(
    private val wanAndroidService: WanAndroidService
) {
    fun wendaDataSource():PagingSource<Int,Article> = DataSource(wanAndroidService::getWenda)
    fun guangchangDataSource():PagingSource<Int,Article> = DataSource(wanAndroidService::getGuangchang)
}

internal class DataSource  constructor(
    val remoteApi: suspend (Int,Int)-> NetResponse<PagedData<Article>>
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNum = params.key ?: 0 //分页加载的页数
            val response = remoteApi(pageNum,params.loadSize)//网络接口获取分页数据
            response.handleResponse { pagedData ->
                //prevKey 上一页页数；
                val prevKey = if (pageNum == 0) null else pageNum.minus(1)
                // nextKey 下一页页数; pagedData.over 文章加载完
                val nextKey = if (pagedData.over) null else pageNum.plus(1)

                LoadResult.Page(data = pagedData.datas, prevKey = prevKey, nextKey = nextKey)
            }
        }catch (e:Throwable){
            if (e is CancellationException) throw e
            LoadResult.Error(e)
        }
    }
}