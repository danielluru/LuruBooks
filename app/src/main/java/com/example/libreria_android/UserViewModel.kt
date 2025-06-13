package com.example.libreria_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import com.example.libreria_android.DB.Entities.UserWithBooks
import com.example.libreria_android.DB.Entities.UsersEntity
import com.example.libreria_android.DB.Repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val users: LiveData<List<UsersEntity>> = userRepository.getUsers().asLiveData()

    fun insertUser(user: UsersEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun updateUser(user: UsersEntity) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(user: UsersEntity) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    suspend fun getUserWithBooks(userId: Int): UserWithBooks {
        return userRepository.getUserWithBooks(userId)
    }

    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        userRepository.insertUserBookCrossRef(crossRef)
    }
}