package com.example.libreria_android.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.libreria_android.DB.Entities.BooksDao
import com.example.libreria_android.DB.Entities.BooksEntity
import com.example.libreria_android.DB.Entities.UserBookCrossRef
import com.example.libreria_android.DB.Entities.UserDao
import com.example.libreria_android.DB.Entities.UsersEntity


// Database class for the app, defining the entities and DAOs
@Database(entities = [
    BooksEntity::class,
    UsersEntity::class,
    UserBookCrossRef::class
], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun userDao(): UserDao


    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build().also { INSTANCE = it }
                instance
            }
        }
    }
}