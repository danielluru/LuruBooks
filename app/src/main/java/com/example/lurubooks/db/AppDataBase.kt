package com.example.lurubooks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lurubooks.db.entities.BooksDao
import com.example.lurubooks.db.entities.BooksEntity
import com.example.lurubooks.db.entities.UserBookCrossRef
import com.example.lurubooks.db.entities.UserBookDao
import com.example.lurubooks.db.entities.UserDao
import com.example.lurubooks.db.entities.UsersEntity


// Database class for the app, defining the entities and DAOs
@Database(
    entities = [
        BooksEntity::class,
        UsersEntity::class,
        UserBookCrossRef::class
    ], version = 4
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun userDao(): UserDao
    abstract fun userBookDao(): UserBookDao


    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database.db"
                ).fallbackToDestructiveMigration(false).build().also { INSTANCE = it }
                instance
            }
        }
    }
}