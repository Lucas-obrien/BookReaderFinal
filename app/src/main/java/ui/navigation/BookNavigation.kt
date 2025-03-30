//import ui.HomeScreen

///*
// * Copyright (C) 2023 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.example.inventory.ui.navigation
//
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//
///**
// * Provides Navigation graph for the application.
// */

//

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ui.BookReaderApp
//import ui.ManageBookScreen

@Composable
fun NavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            BookReaderApp(navController) // Use your homeScreen composable
        }
        composable("manageBookScreen") {
            ManageBookScreen(navController) // Use your homeScreen composable
        }
    }

}


//
//@Composable
//fun BookNavigationHost(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//){
//    NavHost(
//        navController = navController,
//        startDestination = HomeDestination.route,
//        modifier = modifier
//    ){
//        composable(route = HomeDestination.route) {
//            BookReaderApp()
//        }
//    }
//}


//@Composable
//fun InventoryNavHost(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//) {
//    NavHost(
//        navController = navController,
//        startDestination = HomeDestination.route,
//        modifier = modifier
//    ) {
//
//    }
//}