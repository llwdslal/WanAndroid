package com.rock.wan_domain.interactor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rock.lib_base.arch.PagingInteractor
import com.rock.wan_data.entity.Article
import com.rock.wan_repositroy.datastore.ArticlePagingMediator
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ArticlePagingInteractor @Inject constructor(
    private val articlePagingMediator: ArticlePagingMediator
): PagingInteractor<ArticlePagingInteractor.Params, Article>() {

    data class Params(override val pagingConfig: PagingConfig):Parameters<Article>

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<Article>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = articlePagingMediator
        ){
            articlePagingMediator.pagingSource
        }.flow
    }
}