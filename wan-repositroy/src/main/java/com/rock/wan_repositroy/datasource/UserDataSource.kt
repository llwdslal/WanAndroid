package com.rock.wan_repositroy.datasource

import com.rock.wan_data.remote.WanAndroidService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserDataSource @Inject constructor(
    private val wanAndroidService: WanAndroidService
) {
    suspend fun userLogin(userName:String,password:String) = wanAndroidService.login(userName,password)
    suspend fun userLogout() = wanAndroidService.logout()
}