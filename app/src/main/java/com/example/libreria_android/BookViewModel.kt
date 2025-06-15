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
import com.example.libreria_android.books.Books
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BooksRepository) : ViewModel() {

    val books: LiveData<List<Books>> = bookRepository.getBooks().asLiveData()

    fun insertBook(book: Books) {
        viewModelScope.launch {
            bookRepository.insertUserBook(book)
        }
    }

    fun updateBook(book: Books) {
        viewModelScope.launch {
            bookRepository.updateUserBook(book)
        }
    }

    fun deleteBook(book: BooksEntity) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
        }
    }

    suspend fun getBookById(bookId: String): Books? {
        return bookRepository.getBookById(bookId)
    }

    suspend fun getBookWithUsers(bookId: String): BookWithUsers {
        return bookRepository.getBookWithUsers(bookId)
    }
}

//class BookViewModelFactory(
//    private val repository: BooksRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return BookViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}