package com.example.libreria_android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria_android.db.entities.BookWithUsers
import com.example.libreria_android.db.entities.BooksEntity
import com.example.libreria_android.db.repositories.BooksRepository
import com.example.libreria_android.books.Books
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BooksRepository) : ViewModel() {

    private val _books = MutableStateFlow<List<Books>>(emptyList())
    val books: StateFlow<List<Books>> = _books.asStateFlow()

    init {
        viewModelScope.launch {
            bookRepository.getBooks().collect { booksList ->
                _books.value = booksList
            }
        }
    }

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