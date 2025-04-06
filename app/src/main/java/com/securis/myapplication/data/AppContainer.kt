package com.securis.myapplication.data

import android.content.Context

class AppDataContainer(private val context: Context) {

    // Lazy initialization of the AppDatabase
    private val appDatabase by lazy {
        AppDatabase.getDatabase(context.applicationContext)
    }

    // Initialize the repository after the database is initialized
    val booksRepository: BooksRepository by lazy {
        // Make sure the AppDatabase instance is initialized before accessing the repository
        BooksRepositoryImpl(appDatabase.bookDao())
    }

}
