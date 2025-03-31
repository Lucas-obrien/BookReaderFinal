package com.securis.myapplication.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.securis.myapplication.data.Book
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.AppViewModelProvider
import com.securis.myapplication.ui.HomeViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}
//
//enum class BookReaderScreen(@StringRes val title: Int){
//    Home(title = R.string.app_name),
//    Recommendations(title = R.string.recommendations),
//    Management(title = R.string.manage)
//}
//
//object HomeDestination : NavigationDestination {
//    override val route = "home"
//    override val titleRes = R.string.app_name
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderHomeScreen(
    navigateToBookEntry: () -> Unit,
    navigateToBookUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val books = listOf(
        Book(id = 1, title = "1984", author = "George Orwell"),
        Book(id = 2, title = "Brave New World", author = "Aldous Huxley"),
        Book(id = 3, title = "Fahrenheit 451", author = "Ray Bradbury")
    )
    // Use collected books in the UI
    Column(modifier = Modifier.fillMaxWidth()) {
        HomeBody(
            bookList = books,
            onItemClick = {},
            contentPadding = PaddingValues(16.dp)
        )
//        MenuButton(navController)
    }
}

@Composable
private fun HomeBody(
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
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
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
        onItemClick = {},
        contentPadding = PaddingValues(16.dp)
    )
    MenuButton(navController)
}
}