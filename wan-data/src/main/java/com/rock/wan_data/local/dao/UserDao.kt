package com.rock.wan_data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.rock.wan_data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao:BaseDao<User>() {
    @Query("SELECT * FROM User LIMIT 1")
    abstract fun getUser(): Flow<User?>

    @Query("DELETE FROM User")
    abstract suspend fun clear()
}