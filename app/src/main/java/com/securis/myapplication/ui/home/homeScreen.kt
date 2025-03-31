package com.securis.myapplication.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.securis.myapplication.R
//import data.book
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.securis.myapplication.data.Book
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.AppViewModelProvider
import com.securis.myapplication.ui.BookReaderTopAppBar

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderHomeScreen(
    navigateToBookEntry: () -> Unit,
    navigateToBookUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Handle the state of the top bar scroll behavior
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the home UI state from the viewModel
    val homeUiState by viewModel.homeUiState.collectAsState(initial = HomeUiState()) // Provide a default HomeUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookReaderTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToBookEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.book_entry_title)
                )
            }
        },
    ) { innerPadding ->
        // Check that bookList is not null or empty before passing it to HomeBody
        HomeBody(
            bookList = homeUiState.bookList ?: emptyList(), // Use an empty list if bookList is null
            onBookClick = navigateToBookUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}








//    val books = listOf(
//        Book(id = 1, title = "1984", author = "George Orwell"),
//        Book(id = 2, title = "Brave New World", author = "Aldous Huxley"),
//        Book(id = 3, title = "Fahrenheit 451", author = "Ray Bradbury")
//    )
//    // Use collected books in the UI
//    Column(modifier = Modifier.fillMaxWidth()) {
//        HomeBody(
//            bookList = books,
//            onItemClick = {},
//            contentPadding = PaddingValues(16.dp)
//        )
//        MenuButton(navController)
//    }

@Composable
private fun HomeBody(
    bookList: List<Book>,
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (bookList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            BookList(
                bookList = bookList,
                onItemClick = { onBookClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun BookList(
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


@Composable
fun MenuButton(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("manageBookScreen")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Manage Books",
                    style = TextStyle(
                        fontSize = 32.sp, // Initial font size
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeBody() {
    val navController = rememberNavController()
    val sampleBooks = listOf(
        Book(id = 1, title = "1984", author = "George Orwell"),
        Book(id = 2, title = "Brave New World", author = "Aldous Huxley"),
        Book(id = 3, title = "Fahrenheit 451", author = "Ray Bradbury")
    )
    Column(modifier = Modifier.fillMaxWidth()) {
    HomeBody(
        bookList = sampleBooks,
        onBookClick = {},
        contentPadding = PaddingValues(16.dp)
    )
    MenuButton(navController)
}
}

@Preview
@Composable
fun PreviewBookHomeScreen(){
    BookReaderHomeScreen(
        navigateToBookEntry = { /* mock navigation */ },
        navigateToBookUpdate = { /* mock navigation */ },
        modifier = Modifier,
        viewModel = viewModel(factory = AppViewModelProvider.Factory) // Use the default viewModel factory
    )
}