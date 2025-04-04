package com.securis.myapplication.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.securis.myapplication.data.*


class HomeViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        booksRepository.getFirstThreeBooksStream().map { HomeUiState(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), HomeUiState())

    fun refreshBooksFromApi() {
        viewModelScope.launch {
            booksRepository.refreshBooksFromApi()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class HomeUiState(val bookList: List<Book> = listOf())
