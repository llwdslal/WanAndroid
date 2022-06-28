package com.rock.wan_data.di

import com.rock.wan_data.remote.RetrofitClient
import com.rock.wan_data.remote.WanAndroidService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class DebugURL
@Qualifier
annotation class PreProductURL
@Qualifier
annotation class ProductURL

@Module
@InstallIn(SingletonComponent::class)
object NetServiceModule {

    @Provides
    @DebugURL
    fun providerDebugBaseUrl():String = WanAndroidService.baseUrlDebug

    @Provides
    @PreProductURL
    fun providerPreProductBaseUrl():String = WanAndroidService.baseUrlPreProduct

    @Provides
    @ProductURL
    fun providerProductBaseUrl():String = WanAndroidService.baseUrlProduct


    @Provides
    @Singleton
    fun providerWanAndroidService(@DebugURL baseUrl:String):WanAndroidService = RetrofitClient.apply {
        init(baseUrl)
    }.createService(WanAndroidService::class.java)
}
