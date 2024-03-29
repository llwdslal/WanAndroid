package com.rock.wan_domain.interactor

import com.rock.lib_base.arch.ResultInteractor
import com.rock.wan_repositroy.BannerRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BannerInteractor @Inject constructor(
    private val bannerRepository: BannerRepository
): ResultInteractor<Unit,Unit>(){
    override suspend fun doWork(params: Unit) = bannerRepository.updateBanner()
}