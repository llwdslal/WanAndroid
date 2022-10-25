package com.rock.ui_home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rock.lib_base.arch.BaseViewModel
import com.rock.lib_base.arch.PAGING_CONFIG
import com.rock.lib_base.arch.collectStatus
import com.rock.wan_data.entity.Article
import com.rock.wan_domain.interactor.ArticlePagingInteractor
import com.rock.wan_domain.interactor.BannerInteractor
import com.rock.wan_domain.interactor.TopicInteractor
import com.rock.wan_domain.observer.BannerObserver
import com.rock.wan_domain.observer.TopicObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    bannerObserver: BannerObserver,
    topicObserver: TopicObserver,
    articlePagingInteractor: ArticlePagingInteractor,
    private val bannerInteractor: BannerInteractor,
    private val topicInteractor: TopicInteractor,
) :BaseViewModel<HomeAction>() {

    init {
        bannerObserver(Unit)
        topicObserver(Unit)
        articlePagingInteractor(ArticlePagingInteractor.Params(PAGING_CONFIG))
        initLoad()
    }

    val articlesPagingDataFlow : Flow<PagingData<Article>> = articlePagingInteractor.flow.cachedIn(viewModelScope)
    val bannerFlow =  bannerObserver.flow
    val topicFlow = topicObserver.flow


    override fun onAction(action: HomeAction) {

    }

    private fun initLoad(){
        viewModelScope.launch {
            bannerInteractor(Unit).collectStatus(invokeCounter)
        }
       viewModelScope.launch {
           topicInteractor(Unit).collectStatus(invokeCounter)
        }

    }

}