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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.securis.myapplication.R
//import data.Book

import androidx.lifecycle.asLiveData
import com.myapplication.ui.MenuButton


@Composable
fun ManageBookApp(navController: NavHostController, context: Context) {


    // Get the Room database instance
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "books_database"
    ).build()

    // Observe the list of books from the database
    val booksLiveData: LiveData<List<Book>> = db.bookDao().getAllBooks().asLiveData()
    val books = booksLiveData.observeAsState(emptyList()).value

    Column(modifier = Modifier.fillMaxWidth()) {
        // Replace sampleBooks with the observed books from the database
        ManageBookBody(
            bookList = books,
            onItemClick = {},
            contentPadding = PaddingValues(16.dp)
        )
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




@Composable
fun ManageBookScreen(navController: NavHostController) {

}


@Preview
@Composable
fun previewManageBook(){
    val navController = rememberNavController()
    ManageBookApp(
        navController,
        context = TODO()
    )
}