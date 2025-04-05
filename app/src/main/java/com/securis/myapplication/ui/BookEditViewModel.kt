package com.securis.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

class BookEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val booksRepository: BooksRepository
) : ViewModel() {

    var bookUiState by mutableStateOf(BookUiState())
        private set

    private val bookId: Int = checkNotNull(savedStateHandle[BookEditDestination.bookIdArg])

    init {
        viewModelScope.launch {
            val book = booksRepository.getBookStream(bookId).firstOrNull()
            if (book != null) {
                bookUiState = book.toBookUiState(isEntryValid = true)
            }
        }
    }

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState = BookUiState(
            bookDetails = bookDetails,
            isEntryValid = validateInput(bookDetails)
        )
    }

    fun updateBook() {
        viewModelScope.launch {
            if (bookUiState.isEntryValid) {
                booksRepository.updateBook(bookUiState.bookDetails.toBook())
            }
        }
    }

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && author.isNotBlank()
        }
    }
}


@Preview
@Composable
fun BookEditViewTest(){

}