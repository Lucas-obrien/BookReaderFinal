package com.securis.myapplication.ui.screens

import LocalCustomColors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.data.Book
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.BookReaderTopAppBar
import com.securis.myapplication.ui.viewModels.HomeUiState
import com.securis.myapplication.ui.viewModels.HomeViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderHomeScreen(
    navigateToBookUpdate: (Int) -> Unit,
    navigateToSearchBooks: () -> Unit,
    navigateToRecommendedBooks: () -> Unit,
    navigateToStats: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState(initial = HomeUiState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookReaderTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        HomeBody(
            bookList = homeUiState.bookList,
            onBookClick = navigateToBookUpdate,
            onSearchBooksClick = navigateToSearchBooks,
            onRecommendedButtonClick = navigateToRecommendedBooks,
            onStatsButtonClick = navigateToStats,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun HomeBody(
    bookList: List<Book>,
    onBookClick: (Int) -> Unit,
    onSearchBooksClick: () -> Unit,
    onRecommendedButtonClick: () -> Unit,
    onStatsButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxListHeight = screenHeight * 0.5f

    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
    ) {
        if (bookList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
            )
        } else {
            Column(
                modifier = modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = maxListHeight)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
                ) {
                    items(bookList, key = { it.id }) { book ->
                        BookItem(
                            book = book,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onBookClick(book.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                SearchButton(onClick = onSearchBooksClick)
                Spacer(modifier = Modifier.height(16.dp))
                RecommendedButton(onClick = onRecommendedButtonClick)
                Spacer(modifier = Modifier.height(16.dp))
                StatisticsButton(onClick = onStatsButtonClick)
            }
        }
    }
}

@Composable
private fun BookItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth()
                .height(72.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = book.title.take(30),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = book.genre.take(20),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = book.author.take(20),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "⭐ ${book.apiRating}/5",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun SearchButton(onClick: () -> Unit = {}) {
    val customColors = LocalCustomColors.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = customColors.buttonBackground,
            contentColor = customColors.buttonText
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Search Books",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RecommendedButton(onClick: () -> Unit = {}) {
    val customColors = LocalCustomColors.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = customColors.buttonBackground,
            contentColor = customColors.buttonText
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Recommended Books",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun StatisticsButton(onClick: () -> Unit = {}) {
    val customColors = LocalCustomColors.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = customColors.buttonBackground,
            contentColor = customColors.buttonText
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Book Statistics",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBookHomeScreen() {
    BookReaderHomeScreen(
        navigateToBookUpdate = { },
        navigateToSearchBooks = { },
        navigateToRecommendedBooks = {},
        navigateToStats = {},
        modifier = Modifier,
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}