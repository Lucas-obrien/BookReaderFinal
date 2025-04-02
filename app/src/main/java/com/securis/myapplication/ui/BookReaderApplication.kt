
package com.securis.myapplication.ui


import android.app.Application
import com.securis.myapplication.data.AppDataContainer

class BookReaderApplication : Application() {
    lateinit var container: AppDataContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }


}

