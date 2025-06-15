//package com.example.libreria_android
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModelProvider
//import com.example.libreria_android.DB.Entities.BooksEntity
//import com.example.libreria_android.DB.Entities.UsersEntity
//import com.example.libreria_android.DB.Entities.UserBookCrossRef
//import com.example.libreria_android.DB.Repositories.UserBookRepository
//import kotlinx.coroutines.launch
//
//class UserBookViewModel(private val repository: UserBookRepository) : ViewModel() {
//
//    private val _booksForUser = MutableLiveData<List<BooksEntity>>()
//    val booksForUser: LiveData<List<BooksEntity>> get() = _booksForUser
//
//    private val _usersForBook = MutableLiveData<List<UsersEntity>>()
//    val usersForBook: LiveData<List<UsersEntity>> get() = _usersForBook
//
//    fun fetchBooksForUser(userId: Int) {
//        viewModelScope.launch {
//            val books = repository.getBooksForUser(userId)
//            _booksForUser.postValue(books)
//        }
//    }
//
//    fun fetchUsersForBook(bookId: Int) {
//        viewModelScope.launch {
//            val users = repository.getUsersForBook(bookId)
//            _usersForBook.postValue(users)
//        }
//    }
//
//    fun addUserBookRelation(userId: Int, bookId: Int, status: UserBookCrossRef) {
//        viewModelScope.launch {
//            repository.addUserBookRelation(status)
//        }
//    }
//}
//
//class UserBookViewModelFactory(private val repository: UserBookRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserBookViewModel::class.java)) {
//            return UserBookViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
