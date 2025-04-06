
package com.securis.myapplication.ui.screens
import AppTopBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.data.Book
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.viewModels.FilterDropdown
import com.securis.myapplication.ui.viewModels.SearchBooksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi



object BookSearchDestination : NavigationDestination {
    override val route = "book_search"
    override val titleRes = R.string.search_book_title
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchBookScreen(
    navigateToBookEntry: () -> Unit,
    onBookSearchClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchBooksViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val uiState = viewModel.searchUiState.collectAsState()
    var selectedFilter by remember { mutableStateOf("Title") }
    var searchQuery by remember { mutableStateOf("") }

    if (isRefreshing) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Please wait") },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Refreshing database...")
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(title = "Search Books", onBackClick = navigateBack)
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(onClick = { navigateToBookEntry() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }

                FloatingActionButton(onClick = { viewModel.refreshBooks() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
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
                        BookSearchItem(book = book, onClick = { onBookSearchClick(book.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun BookSearchItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Genre: ${book.genre}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

