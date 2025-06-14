package com.example.libreria_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    suspend fun getBookById(bookId: String): BooksEntity? {
        return bookRepository.getBookById(bookId)
    }

    suspend fun getBookWithUsers(bookId: String): BookWithUsers {
        return bookRepository.getBookWithUsers(bookId)
    }

    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef) {
        bookRepository.insertUserBookCrossRef(crossRef)
    }

    // New: Insert or update UserBookCrossRef for status/favorite
    fun upsertUserBookCrossRef(crossRef: UserBookCrossRef) {
        viewModelScope.launch {
            bookRepository.insertUserBookCrossRef(crossRef)
        }
    }
}

class BookViewModelFactory(
    private val repository: BooksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}