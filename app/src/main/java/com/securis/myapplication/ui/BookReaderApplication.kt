
package com.securis.myapplication.ui


import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.securis.myapplication.data.AppDataContainer

class BookReaderApplication : Application() {
    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
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

