package com.securis.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ManageBooksViewModel(
    booksRepository: BooksRepository
) : ViewModel() {

    val manageUiState: StateFlow<ManageUiState> =
        booksRepository.getAllBooksStream()
            .map { ManageUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ManageUiState())

}

data class ManageUiState(val bookList: List<Book> = emptyList())
