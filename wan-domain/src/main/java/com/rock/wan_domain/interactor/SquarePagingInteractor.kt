package com.rock.wan_domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rock.lib_base.arch.PagingInteractor
import com.rock.wan_data.entity.Article
import com.rock.wan_repositroy.datasource.ArticlePagingSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class SquarePagingInteractor @Inject constructor(
    private val dataSource: ArticlePagingSource
) : PagingInteractor<SquarePagingInteractor.Params, Article>() {


    override fun createObservable(params: Params): Flow<PagingData<Article>> {
        return Pager(params.pagingConfig) { dataSource.squareDataSource() }.flow
    }

    data class Params(override val pagingConfig: PagingConfig) : Parameters<Article>
}