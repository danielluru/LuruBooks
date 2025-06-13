package com.example.libreria_android.DB

import android.content.Context
import com.example.libreria_android.DB.Repositories.BooksRepositoryImpl
import com.example.libreria_android.DB.Repositories.UserRepositoryImpl

class AppContainer(context: Context) {
    private val database = AppDataBase.getDatabase(context)
    private val booksDao = database.booksDao()
    private val userDao = database.userDao()
    private val booksRepository = BooksRepositoryImpl(booksDao)
    private val userRepository = UserRepositoryImpl(userDao)

    fun provideBooksRepository(): BooksRepositoryImpl {
        return booksRepository
    }

    fun provideUserRepository(): UserRepositoryImpl {
        return userRepository
    }
}