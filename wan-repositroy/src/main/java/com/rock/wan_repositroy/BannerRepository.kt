package com.rock.wan_repositroy


import com.rock.wan_data.remote.handleResponse
import com.rock.wan_data.entity.Banner
import com.rock.wan_repositroy.datasource.BannerDataSource
import com.rock.wan_repositroy.datastore.BannerDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepository @Inject constructor(
    private val bannerDataStore: BannerDataStore,
    private val bannerDataSource: BannerDataSource
) {
    suspend fun updateBanner() {
        bannerDataSource.banners().handleResponse {
            bannerDataStore.saveBanner(it)
        }
    }
    fun observeBanner(): Flow<List<Banner>> = bannerDataStore.getBanner()
}