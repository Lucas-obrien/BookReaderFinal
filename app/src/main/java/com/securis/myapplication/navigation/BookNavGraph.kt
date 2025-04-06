package com.example.inventory.ui.navigation

import com.securis.myapplication.ui.screens.BookStatsDestination
import com.securis.myapplication.ui.screens.StatsScreenRoute
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.screens.BookDetailScreen
import com.securis.myapplication.ui.screens.BookDetailsDestination
import com.securis.myapplication.ui.screens.BookEditDestination
import com.securis.myapplication.ui.screens.BookEditScreen
import com.securis.myapplication.ui.screens.BookEntryDestination
import com.securis.myapplication.ui.screens.BookEntryScreen
import com.securis.myapplication.ui.screens.BookRecommendDestination
import com.securis.myapplication.ui.screens.BookSearchDestination
import com.securis.myapplication.ui.screens.RecommendedBookScreen
import com.securis.myapplication.ui.screens.SearchBookScreen
import com.securis.myapplication.ui.screens.BookReaderHomeScreen
import com.securis.myapplication.ui.screens.HomeDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi


// Provides Navigation graph for the application.
 
@OptIn(ExperimentalCoroutinesApi::class)
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
                navigateToBookUpdate = { navController.navigate("${BookDetailsDestination.route}/$it") },
                navigateToSearchBooks = { navController.navigate(BookSearchDestination.route) },
                navigateToRecommendedBooks = {navController.navigate(BookRecommendDestination.route)},
                navigateToStats = {navController.navigate(BookStatsDestination.route)},
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
                navigateToEditBook = { navController.navigate("${BookEditDestination.route}/$it") },
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

        composable(route = BookSearchDestination.route) {
            SearchBookScreen(
                navigateToBookEntry = { navController.navigate(BookEntryDestination.route) },
                onBookSearchClick = { bookId ->
                    navController.navigate("${BookDetailsDestination.route}/$bookId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = BookRecommendDestination.route){
            RecommendedBookScreen(
                onBookSearchClick = { bookId ->
                    navController.navigate("${BookDetailsDestination.route}/$bookId")
                },
                navigateBack = { navController.popBackStack() }

            )
        }
        composable(route = BookStatsDestination.route){
            StatsScreenRoute(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}
