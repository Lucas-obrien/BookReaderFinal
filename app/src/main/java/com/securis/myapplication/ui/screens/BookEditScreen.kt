package com.securis.myapplication.ui.screens

import BookReaderTheme
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.BookReaderTopAppBar
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.viewModels.BookEditViewModel


object BookEditDestination : NavigationDestination {
    override val route = "book_edit"
    override val titleRes = R.string.book_edit_title
    const val bookIdArg = "bookId"
    val routeWithArgs = "$route/{$bookIdArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            BookReaderTopAppBar(
                title = stringResource(BookEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        BookEntryBody(
            bookUiState = viewModel.bookUiState,
            onBookValueChange = viewModel::updateUiState,
            onSaveClick = {
                viewModel.updateBook()
                navigateBack()
            },
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

@Preview(showBackground = true)
@Composable
fun BookEditScreenPreview() {
    BookReaderTheme {
        BookEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}


