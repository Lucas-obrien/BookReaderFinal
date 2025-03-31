package com.securis.myapplication.ui

import BookDetailViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.navigation.NavigationDestination


object BookEditDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.book_detail_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

}
