package com.example.libreria_android.DB.Repositories

import com.example.libreria_android.DB.Entities.BooksEntity
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import com.example.libreria_android.DB.Entities.UserBookDao
import com.example.libreria_android.DB.Entities.UsersEntity

class UserBookRepository(private val userBookDao: UserBookDao) {

    suspend fun getBooksForUser(userId: Int): List<BooksEntity> {
        return userBookDao.getUserWithBooks(userId).books
    }

    suspend fun getUsersForBook(bookId: Int): List<UsersEntity> {
        return userBookDao.getBookWithUsers(bookId).users
    }

    suspend fun addUserBookRelation(crossRef: UserBookCrossRef) {
        userBookDao.insertUserBookCrossRef(crossRef)
    }
}
