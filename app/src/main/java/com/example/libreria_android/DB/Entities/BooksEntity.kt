package com.example.libreria_android.DB.Entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.libreria_android.books.BookStatus
import com.example.libreria_android.books.Books
import kotlinx.coroutines.flow.Flow


// Represents a book entity in the database
@Entity(tableName = "books")
data class BooksEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val coverUrl: String? = null // Optional field for cover image URL
)
// Data class to represent the relationship between books and users
@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<BooksEntity>>

    @Insert
    suspend fun insertBook(book: BooksEntity)

    @Update
    suspend fun updateBook(book: BooksEntity)

    @Delete
    suspend fun deleteBook(book: BooksEntity)

//    @Transaction
//    @Query("SELECT * FROM books WHERE id = :bookId")
//    fun getBookWithUsers(bookId: String): BookWithUsers

    @Insert
    fun insertUserBookCrossRef(crossRef: UserBookCrossRef)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BooksEntity?
}