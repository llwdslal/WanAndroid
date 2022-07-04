package com.rock.wan_data.remote


import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.Banner
import retrofit2.http.*

interface WanAndroidService {
    companion object{
       const val baseUrlDebug = "https://www.wanandroid.com"
       const val baseUrlPreProduct = "https://www.wanandroid.com"
       const val baseUrlProduct = "https://www.wanandroid.com"
    }
    @GET("article/top/json")
    suspend fun getTopArticles(): NetResponse<List<Article>>

    @GET("banner/json")
    suspend fun getBanners(): NetResponse<List<Banner>>
}

