package com.securis.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Book::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) { // Return Database instance if not initialised, else make database
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "book_database")
                    .fallbackToDestructiveMigration() // Incase of mismatch, delete and rebuild
                    .addCallback(object : RoomDatabase.Callback() {
                        // Only run when DB is first created
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Instance?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    // Prepopulate DB
                                    database.bookDao().insertAll(SampleBooks.list)
                                }
                            }
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
