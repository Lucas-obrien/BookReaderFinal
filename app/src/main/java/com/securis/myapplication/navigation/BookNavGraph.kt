package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.securis.myapplication.ui.AppViewModelProvider
import com.securis.myapplication.ui.BookDetailScreen
import com.securis.myapplication.ui.BookDetailsDestination
import com.securis.myapplication.ui.BookEditDestination
import com.securis.myapplication.ui.BookEditScreen
import com.securis.myapplication.ui.BookEntryDestination
import com.securis.myapplication.ui.BookEntryScreen
import com.securis.myapplication.ui.BookManageDestination
import com.securis.myapplication.ui.ManageBookScreen
import com.securis.myapplication.ui.home.BookReaderHomeScreen
import com.securis.myapplication.ui.home.HomeDestination
import com.securis.myapplication.ui.home.HomeViewModel


// Provides Navigation graph for the application.
 
@Composable
fun BookReaderNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            BookReaderHomeScreen(
                navigateToBookEntry = { navController.navigate(BookEntryDestination.route) },
                navigateToBookUpdate = { navController.navigate("${BookDetailsDestination.route}/$it") },
                navigateToManageBooks = { navController.navigate(BookManageDestination.route) },
                modifier = Modifier,
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
            )
        }
        composable(route = BookEntryDestination.route) {
            BookEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = BookDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(BookDetailsDestination.bookIdArg) {
                type = NavType.IntType
            })
        ) {
            BookDetailScreen(
                navigateToDetailBook = { navController.navigate("${BookDetailsDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = BookEditDestination.routeWithArgs,
            arguments = listOf(navArgument(BookEditDestination.bookIdArg) {
                type = NavType.IntType
            })
        ) {
            BookEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = BookManageDestination.route) {
//            val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
//            val uiState by viewModel.homeUiState.collectAsState()
//
            ManageBookScreen(
                onBookEditClick = { bookId ->
                    navController.navigate("${BookEditDestination.route}/$bookId")
                }
            )

        }
    }
}
