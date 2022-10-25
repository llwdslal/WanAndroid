package com.rock.lib_base.arch

import androidx.paging.PagingConfig

val PAGING_CONFIG = PagingConfig(
    pageSize = 20,
    initialLoadSize = 60 // paging 默认加载 3 页数据
)