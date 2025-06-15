package com.example.lurubooks.db

import android.content.Context
import com.example.lurubooks.db.repositories.BooksRepositoryImpl
import com.example.lurubooks.db.repositories.UserRepositoryImpl

class AppContainer(context: Context) {
    private val database = AppDataBase.getDatabase(context)
    private val booksDao = database.booksDao()
    private val userDao = database.userDao()
    private val userBookDao = database.userBookDao()
    private val booksRepository = BooksRepositoryImpl(booksDao, userBookDao)
    private val userRepository = UserRepositoryImpl(userDao)

    fun provideBooksRepository(): BooksRepositoryImpl {
        return booksRepository
    }

    fun provideUserRepository(): UserRepositoryImpl {
        return userRepository
    }
}