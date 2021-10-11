package com.example.seraqchove.data.repositories

import androidx.lifecycle.LiveData
import com.example.seraqchove.data.interfaces.UserDao
import com.example.seraqchove.data.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun createUser(user: User){
        userDao.createUser(user)
    }

    fun getUserByUsername(username: String): LiveData<List<User>> {
        return userDao.getUserByUsername(username)
    }

    fun getLoggedUser(): LiveData<List<User>>{
        return userDao.getLoggedUser()
    }

    suspend fun updateUserLoggedStatus(userId: Int, status: Boolean){
        return userDao.updateUserLoggedStatus(userId, status)
    }
}