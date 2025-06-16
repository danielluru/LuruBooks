package com.example.lurubooks.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


// Represents a book entity in the database
@Entity(tableName = "books")
data class BooksEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val coverUrl: String? = null
)
// Data class to represent the relationship between books and users
@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<BooksEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BooksEntity)

    @Update
    suspend fun updateBook(book: BooksEntity)

    @Delete
    suspend fun deleteBook(book: BooksEntity)

    @Insert
    fun insertUserBookCrossRef(crossRef: UserBookCrossRef)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BooksEntity?
}