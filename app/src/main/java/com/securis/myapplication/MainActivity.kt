/**
* An Application to track, review and recommend books a user has read
**/

package com.securis.myapplication

import BookReaderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.securis.myapplication.data.BooksDatabaseHelper
import com.securis.myapplication.ui.BookReaderTopApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BookReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookReaderTopApp()
                }

            }
        }
    }
}

@Preview
@Composable
fun DisplayMainTest() {
    BookReaderTheme {
        BookReaderTopApp()
    }
}
