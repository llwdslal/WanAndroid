package com.rock.wan_data.remote


import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.Banner
import com.rock.wan_data.entity.PagedData
import com.rock.wan_data.entity.User
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

    @GET("article/list/{page}/json")
    suspend fun getArticle(@Path("page") page:Int, @Query("page_size") pageSize:Int):NetResponse<PagedData<Article>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username:String,@Field("password") password:String):NetResponse<User>

    @GET("user/logout/json")
    suspend fun logout(): NetResponse<Any>

    @GET("user_article/list/{page}/json")
    suspend fun getWenda(@Path("page") page:Int, @Query("page_size") pageSize:Int):NetResponse<PagedData<Article>>

}

