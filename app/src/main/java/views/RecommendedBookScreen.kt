import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendedBooksScreen() {
    val recommendedBooks = listOf(
        "Sports by Roger Sportsguy",
        "Mystery of the Lost City by Jane Doe",
        "Science for Beginners by Dr. Smith",
        "Fantasy Realms by John Writer"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "BOOK READER - RECOMMENDED FOR YOU",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(16.dp),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Recommended Book List
        recommendedBooks.forEach { book ->
            RecommendedBookItem(book)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Book Management Button
        BookButton("BACK") {
            // Handle book management action
        }
    }
}

@Composable
fun RecommendedBookItem(bookTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .clickable { /* Handle book selection */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = bookTitle,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun BookButton(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .clickable { onClick() },
        color = Color.Black
    )
}


@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    RecommendedBooksScreen() }