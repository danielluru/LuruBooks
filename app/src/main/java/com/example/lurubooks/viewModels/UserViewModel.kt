package com.example.lurubooks.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lurubooks.db.entities.UsersEntity
import com.example.lurubooks.db.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: UsersEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    suspend fun getUserById(userId: Int): UsersEntity? {
        return userRepository.getUserById(userId)
    }
}