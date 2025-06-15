package com.example.lurubooks.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.lurubooks.db.entities.UserBookCrossRef
import com.example.lurubooks.db.entities.UserWithBooks
import com.example.lurubooks.db.entities.UsersEntity
import com.example.lurubooks.db.repositories.UserRepository
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

    suspend fun getUserById(userId: Int): UsersEntity? {
        return userRepository.getUserById(userId)
    }

    suspend fun getUserWithBooks(userId: Int): UserWithBooks {
        return userRepository.getUserWithBooks(userId)
    }

    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        userRepository.insertUserBookCrossRef(crossRef)
    }
}