// ui/BookViewModel.kt
package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.BooksRepository
import data.Book
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BooksRepository) : ViewModel() {

    // Make sure you use StateFlow or LiveData for state management
    private val _allBooks = MutableStateFlow<List<Book>>(emptyList()) // Using MutableStateFlow
    val allBooks: StateFlow<List<Book>> get() = _allBooks

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            repository.getAllBooksStream().collect { books ->
                _allBooks.value = books
            }
        }
    }
}
