package com.securis.myapplication.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.securis.myapplication.ui.BookReaderApplication

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
    fun CreationExtras.bookReaderApplication(): BookReaderApplication {
        val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
        if (application is BookReaderApplication) {
            return application
        } else {
            throw IllegalStateException("Application is not of type BookReaderApplication")
        }
    }

}
