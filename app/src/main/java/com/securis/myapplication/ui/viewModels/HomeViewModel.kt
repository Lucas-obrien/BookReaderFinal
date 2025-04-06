package com.securis.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.*

class HomeViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        booksRepository.getFirstThreeBooksStream()
            .map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}

data class HomeUiState(val bookList: List<Book> = emptyList())
