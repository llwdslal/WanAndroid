package com.rock.wan_domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rock.lib_base.arch.PagingInteractor
import com.rock.wan_data.entity.Article
import com.rock.wan_repositroy.datasource.WendaDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class WendaPagingInteractor @Inject constructor(
    private val wendaDataSource: WendaDataSource
) : PagingInteractor<WendaPagingInteractor.Params, Article>() {


    override fun createObservable(params: Params): Flow<PagingData<Article>> {
        return Pager(params.pagingConfig) { wendaDataSource.newPagingSource() }.flow
    }

    data class Params(override val pagingConfig: PagingConfig) : Parameters<Article>
}