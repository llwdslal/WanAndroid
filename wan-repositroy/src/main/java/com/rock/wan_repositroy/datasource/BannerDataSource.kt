package com.rock.wan_repositroy.datasource

import com.rock.wan_data.remote.WanAndroidService
import javax.inject.Inject


class BannerDataSource @Inject constructor(
    private val service: WanAndroidService
) {
   suspend fun banners() = service.getBanners()
}