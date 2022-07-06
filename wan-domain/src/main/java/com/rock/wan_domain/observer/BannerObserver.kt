package com.rock.wan_domain.observer

import com.rock.lib_base.arch.SubjectInteractor
import com.rock.wan_data.entity.Banner
import com.rock.wan_repositroy.BannerRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class BannerObserver @Inject constructor(
    private val bannerRepository: BannerRepository
): SubjectInteractor<Unit, List<Banner>>() {
    override fun createObservable(params: Unit): Flow<List<Banner>> {
        return  bannerRepository.observeBanner()
    }
}