package com.securis.myapplication.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.data.Book
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.BookReaderTopAppBar
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.viewModels.BookDetailViewModel
import com.securis.myapplication.ui.viewModels.BookDetailsUiState
import com.securis.myapplication.ui.viewModels.toBook


// NavHost directions
object BookDetailsDestination : NavigationDestination {
    override val route = "book_details"
    override val titleRes = R.string.book_detail_title
    const val bookIdArg = "bookId"
    val routeWithArgs = "$route/{$bookIdArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navigateToEditBook: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BookReaderTopAppBar(
                title = stringResource(BookDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        BookDetailsBody(
            bookDetailsUiState = uiState,
            onDelete = { book ->
                viewModel.deleteBook(book)
                navigateBack()
            },
            onBookEditClick = { id -> navigateToEditBook(id) },
            viewModel = viewModel,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}



@Composable
fun BookDetailsBody(
    bookDetailsUiState: BookDetailsUiState,
    onBookEditClick: (Int) -> Unit,
    onDelete: (Book) -> Unit,
    viewModel: BookDetailViewModel,
    modifier: Modifier = Modifier
) {
    val book = bookDetailsUiState.bookDetails.toBook()
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        BookDetails(book = book, modifier = Modifier.fillMaxWidth())

        OutlinedButton(
            onClick = { onBookEditClick(bookDetailsUiState.bookDetails.id) },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.edit_book))
        }

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }

        OutlinedButton(
            onClick = { viewModel.markBookAsStarted(book) },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = !book.started
        ) {
            Text(stringResource(R.string.start_reading))
        }

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete(book)
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}



@Composable
fun BookDetails(
    book: Book, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            BookDetailsRow(
                labelResID = R.string.book,
                bookDetail = book.title,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.author,
                bookDetail = book.author,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.genre,
                bookDetail = book.genre,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.your_review,
                bookDetail = book.userReview?:"",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.rating,
                bookDetail = book.apiRating.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.user_rating,
                bookDetail = book.userRating.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.user_read,
                bookDetail = if (book.read) "Yes" else "No",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            BookDetailsRow(
                labelResID = R.string.user_started,
                bookDetail = if (book.started) "Yes" else "No",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
private fun BookDetailsRow(
    @StringRes labelResID: Int, bookDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = bookDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}
