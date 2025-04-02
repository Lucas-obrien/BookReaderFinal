package com.securis.myapplication.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.*
import kotlinx.coroutines.launch


class BookEntryViewModel(private val booksRepository: BooksRepository) : ViewModel() {

    var bookUiState by mutableStateOf(BookUiState())
        private set

    fun updateUiState(bookDetails: BookDetails) {
        bookUiState =
            BookUiState(bookDetails = bookDetails, isEntryValid = validateInput(bookDetails))
    }

    private fun validateInput(uiState: BookDetails = bookUiState.bookDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && author.isNotBlank()
        }
    }

    fun saveBook() {
        viewModelScope.launch {
            booksRepository.insertBook(bookUiState.bookDetails.toBook())
        }
    }

}

data class BookUiState(
    val bookDetails: BookDetails = BookDetails(),
    val isEntryValid: Boolean = false
)

data class BookDetails(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val review: String = "",
    val genre: String = "",
    val rating: Int = 0
)

fun BookDetails.toBook(): Book =  Book(
    title = title,
    author = author,
    review = review,
    genre = genre,
    rating = rating
)

fun Book.toBookUiState(isEntryValid: Boolean = false): BookUiState = BookUiState(
    bookDetails = this.toBookDetails(),
    isEntryValid = isEntryValid
)

fun Book.toBookDetails(): BookDetails = BookDetails(
    id = id,
    title = title,
    author = author,
    review = review,
    genre = genre,
    rating = rating
)


