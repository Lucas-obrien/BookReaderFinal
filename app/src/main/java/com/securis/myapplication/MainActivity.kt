package com.securis.myapplication


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.securis.myapplication.ui.theme.BookReaderFinalTheme
import model.Book
import views.AppCard
import views.testApp
import views.AppNavigation
import javax.sql.DataSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookReaderFinalTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
//                    AppNavigation()
//                    AppGrid(
//                        modifier = Modifier.padding(
//                            start = dimensionResource(R.dimen.padding_small),
//                            top = dimensionResource(R.dimen.padding_small),
//                            end = dimensionResource(R.dimen.padding_small),
//                        )
//                    )
                }
            }
        }
    }
}

//@Composable
//fun BookReaderScreen() {
//    val context = LocalContext.current
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .background(Color.LightGray, RoundedCornerShape(8.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "BOOK\nREADER", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(80.dp)
//                .background(Color.LightGray, RoundedCornerShape(8.dp))
//                .clickable {
//                    Toast.makeText(context, "Recommended Section Clicked", Toast.LENGTH_SHORT).show()
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "RECOMMENDED FOR YOU", fontSize = 14.sp, fontWeight = FontWeight.Bold)
//                Text(text = "SPORTS BY ROGER SPORTSGUY", fontSize = 14.sp)
//            }
//        }
//        Spacer(modifier = Modifier.height(32.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .background(Color.LightGray, RoundedCornerShape(8.dp))
//                .clickable {
//                    Toast.makeText(context, "Book Management Clicked", Toast.LENGTH_SHORT).show()
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "BOOK MANAGEMENT", fontSize = 14.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}
//
//@Composable
//fun AppCard(book: Book, modifier: Modifier = Modifier){
//    Card {
//        Row {
//            Box {
//                Image(
//                    painter = painterResource(id = book.imageRes),
//                    contentDescription = null,
//                    modifier = modifier
//                        .size(width = 68.dp, height = 68.dp)
//                        .aspectRatio(1f),
//                    contentScale = ContentScale.Crop
//                )
//            }
//            Column {
//                Text(
//                    text = stringResource(id = book.name),
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(
//                        start = dimensionResource(R.dimen.padding_medium),
//                        top = dimensionResource(R.dimen.padding_medium),
//                        end = dimensionResource(R.dimen.padding_medium),
//                        bottom = dimensionResource(R.dimen.padding_small)
//
//                    )
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//
//                    Text(
//                        text = stringResource(book.review),
//                        style = MaterialTheme.typography.labelMedium,
//                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                    )
//
//
//                }
//            }
//        }
//
//
//    }
//}


@Composable
fun AppGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(data.DataSource.books){ book ->
            AppCard(book)

        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun TopicPreview() {
//    BookReaderFinalTheme {
//        BookReaderScreen()
//
//    }
//}

