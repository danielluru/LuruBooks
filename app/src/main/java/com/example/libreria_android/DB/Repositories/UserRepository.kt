package com.example.libreria_android.DB.Repositories

import com.example.libreria_android.DB.Entities.UsersEntity
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import com.example.libreria_android.DB.Entities.UserDao
import com.example.libreria_android.DB.Entities.UserWithBooks
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<UsersEntity>>
    suspend fun insertUser(user: UsersEntity)
    suspend fun updateUser(user: UsersEntity)
    suspend fun deleteUser(user: UsersEntity)
    suspend fun getUserWithBooks(userId: Int): UserWithBooks
    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef)
}

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun getUsers(): Flow<List<UsersEntity>> {
        return userDao.getUsers()
    }

    override suspend fun insertUser(user: UsersEntity) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UsersEntity) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: UsersEntity) {
        userDao.deleteUser(user)
    }

    override suspend fun getUserWithBooks(userId: Int): UserWithBooks {
        return userDao.getUserWithBooks(userId)
    }

    override suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        userDao.insertUserBookCrossRef(crossRef)
    }
}