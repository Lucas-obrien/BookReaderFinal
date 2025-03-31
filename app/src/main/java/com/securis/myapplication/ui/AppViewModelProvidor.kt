package com.securis.myapplication.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.securis.myapplication.ui.home.HomeViewModel

// Factory to generate viewModel for booksRepository

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val booksRepository = bookReaderApplication().container.booksRepository
            BookEntryViewModel(booksRepository)
        }

        initializer {
            val booksRepository = bookReaderApplication().container.booksRepository
            BookDetailViewModel(
                this.createSavedStateHandle(),
                booksRepository
            )
        }

        initializer {
            val booksRepository = bookReaderApplication().container.booksRepository
            HomeViewModel(booksRepository)
        }

        initializer {
            BookEditViewModel(this.createSavedStateHandle())
        }
    }
}

fun CreationExtras.bookReaderApplication(): BookReaderApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BookReaderApplication)
