
package com.securis.myapplication.ui.screens
import AppTopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.viewModels.RecommendedBookViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi



object BookRecommendDestination : NavigationDestination {
    override val route = "book_recommend"
    override val titleRes = R.string.recommended_book_title
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RecommendedBookScreen(
    onBookSearchClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendedBookViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.recommendedUiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        AppTopBar(title = "Recommended Books", onBackClick = navigateBack)
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

