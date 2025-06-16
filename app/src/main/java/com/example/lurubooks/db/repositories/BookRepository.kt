package com.example.lurubooks.db.repositories

import android.util.Log
import com.example.lurubooks.db.entities.BooksDao
import com.example.lurubooks.db.entities.BooksEntity
import com.example.lurubooks.db.entities.UserBookCrossRef
import com.example.lurubooks.db.entities.UserBookDao
import com.example.lurubooks.books.BookStatus
import com.example.lurubooks.books.Books
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface BooksRepository {
    fun getBooks(): Flow<List<Books>>
    suspend fun insertUserBook(book: Books)
    suspend fun updateUserBook(book: Books)
    suspend fun deleteBook(book: BooksEntity)
    suspend fun getBookById(id: String): Books?
}

class BooksRepositoryImpl(
    private val booksDao: BooksDao,
    private val userBookDao: UserBookDao,
    private val userId: Int = Firebase.auth.currentUser?.uid.hashCode()
) : BooksRepository {
   override fun getBooks(): Flow<List<Books>> {
       val booksEntities = booksDao.getBooks()
       return booksEntities.map { it.map { bookEntity ->
           val crossRef = userBookDao.getCrossRef(bookEntity.id, userId)
           Books(
               id = bookEntity.id,
               title = bookEntity.title,
               author = bookEntity.author,
               status = crossRef?.bookStatus ?: BookStatus.NO_GUARDADO,
               isFavorite = crossRef?.bookisFavourite ?: false,
               coverUrl = bookEntity.coverUrl
           )
       } }
   }

    override suspend fun insertUserBook(book: Books) {
        Log.d("BooksRepositoryImpl", "Inserting book: ${book.title} with userId: $userId")
        booksDao.insertBook(bookToEntity(book))
        userBookDao.insertUserBookCrossRef(
            UserBookCrossRef(userId, book.id, book.status, book.isFavorite)
        )
    }

    override suspend fun updateUserBook(book: Books) {
        userBookDao.updateUserBookCrossRef(
            UserBookCrossRef(userId, book.id, book.status, book.isFavorite)
        )
    }

    override suspend fun deleteBook(book: BooksEntity) {
        booksDao.deleteBook(book)
    }

    override suspend fun getBookById(bookId: String): Books? {
        val booksEntity = booksDao.getBookById(bookId)
        val crossRef = userBookDao.getCrossRef(bookId, userId)
        if (booksEntity != null && crossRef != null) {
            return Books(booksEntity.id, booksEntity.title, booksEntity.author, crossRef.bookStatus, crossRef.bookisFavourite, coverUrl = booksEntity.coverUrl)
        }
        return null
    }

    private fun bookToEntity(books: Books): BooksEntity {
        return BooksEntity(
            id = books.id,
            title = books.title,
            author = books.author,
            coverUrl = books.coverUrl
        )
    }
}
