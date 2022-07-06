package com.rock.wan_data.local.dao

import androidx.room.*
import com.rock.wan_data.entity.Banner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class BannerDao : BaseDao<Banner>() {

    @Query("SELECT * FROM Banner")
    protected abstract fun getBanner(): Flow<List<Banner>>

    fun getDistinctBanner():Flow<List<Banner>> = getBanner().distinctUntilChanged()

    //删除表中 ids 之外的记录 （因为 Banner 可能会部分更新）
    @Query("DELETE FROM Banner WHERE id NOT IN (:ids)")
    abstract suspend fun removeCacheNotIn(ids:List<Int>)

    @Transaction
    open suspend fun saveBanners(banners:List<Banner>){
        //先删除已经无效的 Banner
        removeCacheNotIn(banners.map { it.id })
        insertAll(banners)
    }
}