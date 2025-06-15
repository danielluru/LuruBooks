package com.example.libreria_android.DB.Repositories

import com.example.libreria_android.DB.Entities.BookWithUsers
import com.example.libreria_android.DB.Entities.BooksDao
import com.example.libreria_android.DB.Entities.BooksEntity
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface BooksRepository {
    fun getBooks(): Flow<List<BooksEntity>>
    suspend fun insertBook(book: BooksEntity)
    suspend fun updateBook(book: BooksEntity)
    suspend fun deleteBook(book: BooksEntity)
    suspend fun getBookWithUsers(bookId: String): BookWithUsers
    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef)
    suspend fun getBookById(id: String): BooksEntity?
}

class BooksRepositoryImpl(private val booksDao: BooksDao) : BooksRepository {
    override fun getBooks(): Flow<List<BooksEntity>> {
        return booksDao.getBooks()
    }

    override suspend fun insertBook(book: BooksEntity) {
        booksDao.insertBook(book)
    }

    override suspend fun updateBook(book: BooksEntity) {
        booksDao.updateBook(book)
    }

    override suspend fun deleteBook(book: BooksEntity) {
        booksDao.deleteBook(book)
    }

    override suspend fun getBookById(bookId: String): BooksEntity? {
        return booksDao.getBooks().firstOrNull()?.find { it.id == bookId }
    }

    override suspend fun getBookWithUsers(bookId: String): BookWithUsers {
        return booksDao.getBookWithUsers(bookId)
    }

    override suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        booksDao.insertUserBookCrossRef(crossRef)
    }
}
