package com.rock.wan_domain.observer


import com.rock.lib_base.arch.SubjectInteractor
import com.rock.wan_data.entity.User
import com.rock.wan_repositroy.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class UserObserver @Inject constructor(
    private val userRepository: UserRepository
): SubjectInteractor<Unit, User?>() {
    override fun createObservable(params: Unit): Flow<User?> = userRepository.observeUser()
}