// ui/ManageBookScreen.kt
package ui

import BookReaderTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import data.Book
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.dimensionResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R

@Composable
fun ManageBookScreen(
    onEditButtonClicked: () -> Unit,
    viewModel: BookViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val bookList by viewModel.allBooks.collectAsState(initial = emptyList())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onEditButtonClicked,
            Modifier.widthIn(min = 250.dp)
        ) {
            Text("EDIT")
        }

        ManageBody(
            bookList = bookList,
            onItemClick = { bookId ->
                // Handle item click logic here (e.g., show book details)
            }
        )
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ManageBookScreenTest(navigateToBookEntry: () -> Unit,
//                         navigateToBookUpdate: (Int) -> Unit,
//                         modifier: Modifier = Modifier){
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    val manageUiState by viewModel.homeUiState.collectAsState()
//    Scaffold(
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            ManageTopAppBar(
//                title = "Manage Books",
//                canNavigateBack = true,
//                scrollBehavior = scrollBehavior
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = navigateToBookEntry,
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = stringResource(R.string.book_entry_title)
//                )
//            }
//        },
//    ) { innerPadding ->
//        ManageBody(
//            bookList = manageUiState.bookList,
//            onItemClick = navigateToBookUpdate,
//            modifier = modifier.fillMaxSize(),
//            contentPadding = innerPadding,
//        )
//    }
//}


@Composable
private fun ManageBody(
    bookList: List<Book>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (bookList.isEmpty()) {
            Text(
                text = "No books available.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            BookList(
                bookList = bookList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun BookList(
    bookList: List<Book>,
    onItemClick: (Book) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = bookList, key = { it.id }) { book ->
            BookItem(book = book,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(book) })
        }
    }
}

@Composable
private fun BookItem(
    book: Book, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageBodyPreview() {
    BookReaderTheme {
        ManageBody(listOf(
            Book(1, "Game", "Author 1"),
            Book(2, "Pen", "Author 2"),
            Book(3, "TV", "Author 3")
        ), onItemClick = {})
    }
}
