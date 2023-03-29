package com.rock.wan_repositroy


import com.rock.wan_data.remote.handleResponse
import com.rock.wan_repositroy.datasource.UserDataSource
import com.rock.wan_repositroy.datastore.UserDataStore
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userDataStore: UserDataStore,
) {

    fun observeUser() = userDataStore.getLocalUser()

    suspend fun userLogout() {
        userDataSource.userLogout().handleResponse {
            userDataStore.clearCache()
        }
    }

    suspend fun userLogin(userName:String,password:String) {
        userDataSource.userLogin(userName,password).handleResponse {
            userDataStore.saveUser(it)
        }
    }
}