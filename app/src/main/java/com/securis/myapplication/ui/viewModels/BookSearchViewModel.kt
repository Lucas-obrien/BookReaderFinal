package com.securis.myapplication.ui.viewModels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@kotlinx.coroutines.ExperimentalCoroutinesApi
class SearchBooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
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

    fun refreshBooks() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                booksRepository.refreshBooksFromApi()
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    val options = listOf("Title", "Author", "Genre", "Rating")
    var expanded by remember { mutableStateOf(false) }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            OutlinedTextField(
                value = selectedFilter,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filter by") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(64.dp), // Increase clickable height
                textStyle = MaterialTheme.typography.bodyLarge
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onFilterChange(option)
                            expanded = false
                        },
                        modifier = Modifier.height(48.dp)
                    )
                }
            }
        }
    }
}

data class SearchUiState(
    val filteredBooks: List<Book> = emptyList()
)
