package com.example.libreria_android.DB.Entities

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.libreria_android.books.BookStatus

// Data classes to represent the relationships between users and books
data class UserWithBooks(
    @Embedded val user: UsersEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserBookCrossRef::class,
            parentColumn = "userId",
            entityColumn = "bookId"
        )
    )
    val books: List<BooksEntity>
)

data class BookWithUsers(
    @Embedded val book: BooksEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserBookCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "userId"
        )
    )
    val users: List<UsersEntity>
)

// Represents the cross-reference table between users and books
@Entity(
    tableName = "user_book_cross_ref",
    primaryKeys = ["userId", "bookId"],
    foreignKeys = [
        ForeignKey(
            entity = UsersEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BooksEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserBookCrossRef(
    val userId: Int,
    val bookId: String,
    val bookStatus: BookStatus,
    val bookisFavourite: Boolean
)

@Dao
interface UserBookDao {
    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithBooks(userId: Int): UserWithBooks

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookWithUsers(bookId: String): BookWithUsers

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserBookCrossRef(crossRef: UserBookCrossRef)

    @Query("SELECT * FROM user_book_cross_ref WHERE userId = :userId AND bookId = :bookId")
    suspend fun getCrossRef(bookId: String, userId: Int): UserBookCrossRef?

    suspend fun updateUserBookCrossRef(crossRef: UserBookCrossRef) {
        insertUserBookCrossRef(crossRef)
    }
}
