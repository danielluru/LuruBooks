package com.example.lurubooks.db.repositories

import com.example.lurubooks.db.entities.UsersEntity
import com.example.lurubooks.db.entities.UserBookCrossRef
import com.example.lurubooks.db.entities.UserDao
import com.example.lurubooks.db.entities.UserWithBooks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface UserRepository {
    fun getUsers(): Flow<List<UsersEntity>>
    suspend fun insertUser(user: UsersEntity)
    suspend fun updateUser(user: UsersEntity)
    suspend fun deleteUser(user: UsersEntity)
    suspend fun getUserWithBooks(userId: Int): UserWithBooks
    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef)
    suspend fun getUserById(userId: Int): UsersEntity?
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

    override suspend fun getUserById(userId: Int): UsersEntity? {
        return userDao.getUsers().firstOrNull()?.find { it.id == userId }
    }

    override suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        userDao.insertUserBookCrossRef(crossRef)
    }
}