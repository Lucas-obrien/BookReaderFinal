package com.securis.myapplication.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.*


@kotlinx.coroutines.ExperimentalCoroutinesApi
class RecommendedBookViewModel(
    booksRepository: BooksRepository
) : ViewModel() {

    private val _recommendedUiState = MutableStateFlow(RecommendedUiState())
    val recommendedUiState: StateFlow<RecommendedUiState> = _recommendedUiState

    init {
        booksRepository.getTopUnreadBooks()
            .onEach { books ->
                _recommendedUiState.value = RecommendedUiState(filteredBooks = books)
            }
            .launchIn(viewModelScope)
    }
}


data class RecommendedUiState(
    val filteredBooks: List<Book> = emptyList()
)
