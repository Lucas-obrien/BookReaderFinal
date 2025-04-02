
package com.securis.myapplication.ui
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.navigation.NavigationDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi



object BookSearchDestination : NavigationDestination {
    override val route = "book_search"
    override val titleRes = R.string.search_book_title
    const val bookIdArg = "bookId"
    val routeWithArgs = "$route/{$bookIdArg}"
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchBookScreen(
    onBookSearchClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchBooksViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.searchUiState.collectAsState()
    var selectedFilter by remember { mutableStateOf("Title") }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        FilterDropdown(
            selectedFilter = selectedFilter,
            onFilterChange = {
                selectedFilter = it
                viewModel.updateFilter(it, searchQuery)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateFilter(selectedFilter, it)
            },
            label = { Text("Search...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.value.filteredBooks.isEmpty()) {
            Text("No books found", style = MaterialTheme.typography.titleMedium)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.value.filteredBooks, key = { it.id }) { book ->
                    BookManageItem(book = book, onClick = { onBookSearchClick(book.id) })
                }
            }
        }
    }
}
