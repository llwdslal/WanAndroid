package com.rock.wan_repositroy.datastore


import com.rock.wan_data.entity.Banner
import com.rock.wan_data.local.dao.BannerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerDataStore @Inject constructor(
    private val bannerDao: BannerDao
) {

    fun getBanner() = bannerDao.getDistinctBanner()

    suspend fun saveBanner(banners:List<Banner>) = bannerDao.saveBanners(banners)

}