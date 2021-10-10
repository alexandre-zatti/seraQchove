package com.example.seraqchove.data.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.seraqchove.data.AppDatabase
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun getUserByUsername(username: String): LiveData<List<User>> {
        return repository.getUserByUsername(username)
    }

    fun getLoggedUser(): LiveData<List<User>> {
        return repository.getLoggedUser()
    }

    fun createUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.createUser(user)
        }
    }

    fun updateUserLoggedStatus(userId: Int, status: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUserLoggedStatus(userId,status)
        }
    }
}