import android.util.Log
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BookDao
import com.securis.myapplication.ui.network.OpenLibraryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// THIS IS FOR USE WITH OPEN LIBRARY, HOWEVER OPEN LIBRARY HAS SIGNIFICANT NO. OF MISSING VALUES
suspend fun prepopulateBooksFromOpenAPI(bookDao: BookDao) = withContext(Dispatchers.IO) {
    try {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(OpenLibraryApi::class.java)

        val totalPages = 10 // Each page ≈ 100 results
        val newBooks = mutableListOf<Book>()

        for (page in 1..totalPages) {
            delay(100) // ⏱️ Limit to 10 requests/second

            val response = api.searchBooks("fiction", page)
            val books = response.docs.mapNotNull { doc ->
                val title = doc.title ?: return@mapNotNull null
                val author = doc.author_name?.joinToString(", ") ?: "Unknown"

                val exists = bookDao.countByTitleAndAuthor(title, author) > 0
                if (exists) return@mapNotNull null

                var genre = doc.subject?.firstOrNull() ?: "General Fiction"
                if (genre.equals("Unknown", ignoreCase = true)) {
                    genre = "General Fiction"
                }

                Book(
                    title = title,
                    author = author,
                    blurb = "No blurb available.",
                    genre = genre,
                    apiRating = 3, // Open Library doesn't provide this
                    userRating = null,
                    userReview = null,
                    read = false
                )
            }

            newBooks.addAll(books)
        }

        bookDao.insertAll(newBooks)
        Log.d("RefreshBooks", "Added ${newBooks.size} new books.")

    } catch (e: Exception) {
        Log.e("RefreshBooks", "Failed to fetch books: ${e.localizedMessage}")
    }
}
