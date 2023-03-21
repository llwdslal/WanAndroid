package com.rock.wan_data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rock.wan_data.entity.Article
import com.rock.wan_data.entity.ArticleRemoteKey
import com.rock.wan_data.entity.Banner
import com.rock.wan_data.entity.User
import com.rock.wan_data.local.dao.ArticleDao
import com.rock.wan_data.local.dao.ArticleRemoteKeyDao
import com.rock.wan_data.local.dao.BannerDao
import com.rock.wan_data.local.dao.UserDao


@Database(
    entities = [
        Article::class,
        Banner::class,
        ArticleRemoteKey::class,
        User::class
    ],
    version = 1
)
abstract class WanAndroidDB : RoomDatabase(){

    abstract fun bannerDao(): BannerDao
    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): ArticleRemoteKeyDao
    abstract fun userDao():UserDao

    companion object{
        private val instance:WanAndroidDB?= null

        @Synchronized
        fun getDB(context:Context):WanAndroidDB{
            return instance ?: buildDB(context)
        }

        private fun buildDB(context:Context):WanAndroidDB{
            val builder = Room.databaseBuilder(
                context,
                WanAndroidDB::class.java,
                "wanAndroidDB"
            ).fallbackToDestructiveMigration()
            return builder.build()
        }
    }
}