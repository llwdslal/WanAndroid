package com.rock.wan_domain.interactor


import com.rock.lib_base.arch.Interactor
import com.rock.wan_repositroy.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoginInteractor @Inject constructor(
    private val userRepository: UserRepository
): Interactor<LoginInteractor.Params>() {

    data class Params(val username:String,val password:String)

    override suspend fun doWork(params: Params) = userRepository.userLogin(params.username,params.password)
}