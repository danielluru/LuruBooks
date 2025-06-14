package com.example.libreria_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.libreria_android.DB.Entities.BookWithUsers
import com.example.libreria_android.DB.Entities.BooksEntity
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import com.example.libreria_android.DB.Repositories.BooksRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BooksRepository) : ViewModel() {

    val books: LiveData<List<BooksEntity>> = bookRepository.getBooks().asLiveData()

    fun insertBook(book: BooksEntity) {
        viewModelScope.launch {
            bookRepository.insertBook(book)
        }
    }

    fun updateBook(book: BooksEntity) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
        }
    }

    fun deleteBook(book: BooksEntity) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
        }
    }

    suspend fun getBookById(bookId: Int): BooksEntity? {
        return bookRepository.getBookById(bookId)
    }

    suspend fun getBookWithUsers(bookId: Int): BookWithUsers {
        return bookRepository.getBookWithUsers(bookId)
    }

    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        bookRepository.insertUserBookCrossRef(crossRef)
    }
}