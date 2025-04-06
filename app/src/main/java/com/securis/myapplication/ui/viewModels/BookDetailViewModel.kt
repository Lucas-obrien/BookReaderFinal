package com.securis.myapplication.ui.viewModels


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import com.securis.myapplication.ui.screens.BookDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository // removing val here results in errors, ignore warning
) : ViewModel() {


    private val bookId: Int = checkNotNull(savedStateHandle[BookDetailsDestination.bookIdArg])


    val uiState: StateFlow<BookDetailsUiState> =
        booksRepository.getBookStream(bookId)
            .filterNotNull()
            .map {
                BookDetailsUiState(bookDetails = it.toBookDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = BookDetailsUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            booksRepository.deleteBook(book)
        }
    }
    fun markBookAsStarted(book: Book) {
        viewModelScope.launch {
            val updatedBook = book.copy(started = true)
            booksRepository.updateBook(updatedBook)
        }
    }

}


data class BookDetailsUiState(
    val bookDetails: BookDetails = BookDetails()
)
