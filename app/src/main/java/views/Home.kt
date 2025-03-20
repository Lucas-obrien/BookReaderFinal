package views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.securis.myapplication.R
import model.Book


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            testApp(
                navController
            )
        }
        composable(route = "recommended") {
            RecommendedBookScreen(
                navController
            )
        }
    }
}

@Composable
fun testApp(navController: NavController, modifier: Modifier = Modifier){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "BOOK\nREADER", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate("recommended")
//                    Toast.makeText(context, "Recommended Section Clicked", Toast.LENGTH_SHORT).show()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "RECOMMENDED FOR YOU", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "SPORTS BY ROGER SPORTSGUY", fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .clickable {
                    Toast.makeText(context, "Book Management Clicked", Toast.LENGTH_SHORT).show()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "BOOK MANAGEMENT", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AppCard(book: Book, modifier: Modifier = Modifier){
    Card {
        Row {
            Box {
                Image(
                    painter = painterResource(id = book.imageRes),
                    contentDescription = null,
                    modifier = modifier
                        .size(width = 68.dp, height = 68.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(
                    text = stringResource(id = book.name),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_small)

                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = stringResource(book.review),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
                    )


                }
            }
        }


    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier){
    Scaffold(topBar = { TopAppBar(title = {Text("Home Screen")}) }) {
            padding ->
        Column(modifier.fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            Button(onClick = {
                navController.navigate("recommended")
            })
            {
                Text("click")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedBookScreen(navController: NavController, modifier: Modifier = Modifier){
    Scaffold(topBar = { TopAppBar(title = {Text("recommended")}) }) {
            padding ->
        Column(modifier.fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            Button(onClick = {navController.navigate("home")}){
                Text("click")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TopicPreview() {
   AppNavigation()
}