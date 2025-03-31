
@file:OptIn(ExperimentalMaterial3Api::class)
package com.securis.myapplication.ui

import BookReaderTheme
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.ui.navigation.BookReaderNavHost

// Top level function for the entire app

@Composable
fun BookReaderTopApp(navController: NavHostController = rememberNavController()) {
    BookReaderNavHost(navController = navController)
}

@Composable
fun BookReaderTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun DisplayTest() {
    BookReaderTheme {
        BookReaderTopApp()
    }
}