package com.securis.myapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.*


@kotlinx.coroutines.ExperimentalCoroutinesApi
class SearchBooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _filterType = MutableStateFlow("Title")
    private val _query = MutableStateFlow("")
    private val _searchUiState = MutableStateFlow(SearchUiState())

    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    init {
        combine(_filterType, _query) { filter, query ->
            filter to query.trim()
        }.flatMapLatest { (filter, query) ->
            when (filter) {
                "Title" -> booksRepository.searchBooksByTitle("%$query%")
                "Author" -> booksRepository.searchBooksByAuthor("%$query%")
                "Genre" -> booksRepository.searchBooksByGenre("%$query%")
                "Rating" -> {
                    val rating = query.toFloatOrNull()
                    if (rating != null) {
                        booksRepository.searchBooksByMinRating(rating)
                    } else {
                        flowOf(emptyList())
                    }
                }
                else -> flowOf(emptyList())
            }
        }.onEach { result ->
            _searchUiState.value = SearchUiState(filteredBooks = result)
        }.launchIn(viewModelScope)
    }

    fun updateFilter(filter: String, query: String) {
        _filterType.value = filter
        _query.value = query
    }
}


@Composable
fun FilterDropdown(
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val filterOptions = listOf("Title", "Author", "Genre", "Rating")

    Box {
        TextButton(onClick = { expanded = true }) {
            Text("Filter: $selectedFilter")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filterOptions.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(filter) },
                    onClick = {
                        onFilterChange(filter)
                        expanded = false
                    }
                )
            }
        }
    }
}

data class SearchUiState(
    val filteredBooks: List<Book> = emptyList()
)
