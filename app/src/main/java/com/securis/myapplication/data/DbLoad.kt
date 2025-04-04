package com.securis.myapplication.data

import com.securis.myapplication.ui.network.GoogleBooksApi
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun prepopulateBooksFromApi(bookDao: BookDao) = withContext(Dispatchers.IO) {
    try {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(GoogleBooksApi::class.java)

        val totalToFetch = 120
        val pageSize = 40
        val newBooks = mutableListOf<Book>()

        for (start in 0 until totalToFetch step pageSize) {
            val response = api.searchBooks("fiction", maxResults = pageSize, startIndex = start)
            val books = response.items?.mapNotNull { item ->
                val info = item.volumeInfo
                val title = info.title ?: return@mapNotNull null
                val author = info.authors?.joinToString(", ") ?: "Unknown"

                // Skip duplicates
                val exists = bookDao.countByTitleAndAuthor(title, author) > 0
                if (exists) return@mapNotNull null

                Book(
                    title = title,
                    author = author,
                    blurb = info.description?.take(300) ?: "No blurb available.",
                    genre = info.categories?.firstOrNull() ?: "Unknown",
                    rating = (info.averageRating ?: 3.0).toInt(),
                    review = null,
                    read = false
                )
            } ?: emptyList()

            newBooks.addAll(books)
        }

        bookDao.insertAll(newBooks)
        Log.d("RefreshBooks", "Added ${newBooks.size} new books.")

    } catch (e: Exception) {
        Log.e("RefreshBooks", "Failed to fetch books: ${e.localizedMessage}")
    }
}
