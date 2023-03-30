package com.rock.ui_discovery

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rock.lib_base.arch.PAGING_CONFIG
import com.rock.lib_compose.arch.ComposeViewModel
import com.rock.wan_data.entity.Article
import com.rock.wan_domain.interactor.WendaPagingInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val wendaPagingInteractor: WendaPagingInteractor,
):ComposeViewModel(){
    init {
        wendaPagingInteractor(WendaPagingInteractor.Params(PAGING_CONFIG))
    }

    val selectedTabIndexState =  mutableStateOf(0)

    val wendaPagingDataFlow : Flow<PagingData<Article>> = wendaPagingInteractor.flow.cachedIn(viewModelScope)
}