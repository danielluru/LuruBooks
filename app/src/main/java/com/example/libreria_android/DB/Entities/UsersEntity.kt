package com.example.libreria_android.DB.Entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


// Represents a user entity in the database
@Entity(tableName = "users")
data class UsersEntity (
    @PrimaryKey val id: Int,
    val email: String,
    val password: String
)

// Data class to represent the relationship between users and books
@Dao
interface UserDao{
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UsersEntity>>

    @Insert
    suspend fun insertUser(user: UsersEntity)

    @Update
    suspend fun updateUser(user: UsersEntity)

    @Delete
    suspend fun deleteUser(user: UsersEntity)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithBooks(userId: Int): UserWithBooks

    @Insert
    fun insertUserBookCrossRef(crossRef: UserBookCrossRef)
}