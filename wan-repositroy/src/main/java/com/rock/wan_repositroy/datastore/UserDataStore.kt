package com.rock.wan_repositroy.datastore

import androidx.room.withTransaction
import com.rock.wan_data.entity.User
import com.rock.wan_data.local.WanAndroidDB
import com.rock.wan_data.local.dao.UserDao

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserDataStore @Inject constructor(
    private val userDao: UserDao,
    private val db: WanAndroidDB,
) {
    fun getLocalUser() = userDao.getUser()

    suspend fun saveUser(user: User){
        db.withTransaction {
            userDao.clear()
            userDao.insert(user)
        }
    }

    suspend fun clearCache(){
        userDao.clear()
    }
}