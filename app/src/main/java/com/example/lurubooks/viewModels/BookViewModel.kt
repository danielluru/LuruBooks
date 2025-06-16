package com.example.lurubooks.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lurubooks.books.BookStatus
import com.example.lurubooks.books.Books
import com.example.lurubooks.db.entities.BooksEntity
import com.example.lurubooks.db.repositories.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    fun loadBooks() {
        viewModelScope.launch {
            _books.value = bookRepository.getBooks().first()
        }
    }

    fun toggleFavorite(book: Books) {
        viewModelScope.launch {
            val currentBook = getBookById(book.id)
            if (currentBook != null) {
                val updatedBook = currentBook.copy(isFavorite = !currentBook.isFavorite)
                updateBook(updatedBook)
                loadBooks()
            } else {
                insertBook(book.copy(isFavorite = true))
            }
        }
    }

    fun changeBookStatus(book: Books, newStatus: BookStatus) {
        viewModelScope.launch {
            val currentBook = getBookById(book.id)
            if (currentBook != null) {
                val updatedBook = currentBook.copy(status = newStatus)
                updateBook(updatedBook)
                loadBooks()
            } else {
                insertBook(book.copy(status = newStatus))
            }
        }
    }

    suspend fun getBookById(bookId: String): Books? {
        return bookRepository.getBookById(bookId)
    }

}