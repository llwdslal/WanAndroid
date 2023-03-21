package com.rock.wan_data.di

import com.rock.wan_data.local.WanAndroidDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun bannerDao(db:WanAndroidDB) = db.bannerDao()

    @Provides
    @Singleton
    fun articleDao(db:WanAndroidDB) = db.articleDao()

    @Provides
    @Singleton
    fun articleRemoteKeyDao(db:WanAndroidDB) = db.articleRemoteKeyDao()

    @Provides
    @Singleton
    fun userDao(db:WanAndroidDB) = db.userDao()

}