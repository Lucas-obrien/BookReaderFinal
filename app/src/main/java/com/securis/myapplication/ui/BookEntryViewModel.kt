
package com.securis.myapplication.ui

import BookDetails
import BookUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.securis.myapplication.data.Book


import com.securis.myapplication.data.BooksRepository
import toBook

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
    suspend fun saveItem() {
        if (validateInput()) {
            booksRepository.insertBook(bookUiState.bookDetails.toBook())
        }
    }

    fun saveBook() {
//        TODO("Not yet implemented")
    }

    data class BookUiState(
        val bookDetails: BookDetails = BookDetails(),
        val isEntryValid: Boolean = false
    )

    data class BookDetails(
        val id: Int = 0,
        val title: String = "",
        val author: String = "",
    )

    fun BookDetails.toBook(): Book =  Book(
        id = id,
        title = title,
        author = author
    )

    fun Book.toBookUiState(isEntryValid: Boolean = false): BookUiState = BookUiState(
        bookDetails = this.toBookDetails(),
        isEntryValid = isEntryValid
    )

    fun Book.toBookDetails(): BookDetails = BookDetails(
        id = id,
        title = title,
        author = author
    )


}