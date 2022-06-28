package com.rock.wan_data.di

import android.content.Context
import com.rock.wan_data.local.WanAndroidDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideWanAndroidDB(@ApplicationContext context: Context):WanAndroidDB = WanAndroidDB.getDB(context)
}