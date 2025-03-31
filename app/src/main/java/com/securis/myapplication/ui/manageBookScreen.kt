/*
    CURRENTLY WORKING ON IMPLEMENTING FUNCTIONALITY
 */


package com.securis.myapplication.ui
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.securis.myapplication.R
import com.securis.myapplication.data.AppDatabase
import com.securis.myapplication.data.Book
import com.securis.myapplication.ui.home.MenuButton


@Composable
fun ManageBookApp(navController: NavHostController, context: Context) {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "books_database"
    ).build()

    val books = db.bookDao().getAllBooks().collectAsState(initial = emptyList()).value

    Column(modifier = Modifier.fillMaxWidth()) {
        MenuButton(navController)
    }
}

@Composable
private fun ManageBookBody(
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
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            ) {
                // Use 'key' to uniquely identify each book using its 'id'
                items(bookList, key = { it.id }) { book ->
                    Text(
                        text = "${book.title} by ${book.author}",
                        modifier = Modifier.clickable { onItemClick(book.id) }
                    )
                }
            }
        }
    }
}


