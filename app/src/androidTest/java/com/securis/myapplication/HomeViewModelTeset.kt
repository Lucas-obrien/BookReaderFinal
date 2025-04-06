package com.securis.myapplication.ui.viewModels

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val sampleBooks = listOf(
        Book(
            id = 1,
            title = "Book One",
            author = "Author A",
            blurb = "Blurb 1",
            genre = "Fiction",
            apiRating = 5,
            userRating = 4,
            apiReview = "Solid book",
            userReview = "Enjoyed it",
            read = true,
            started = true
        )
    )

    private val fakeRepo = object : BooksRepository {
        override fun getFirstThreeBooksStream(): Flow<List<Book>> = flowOf(sampleBooks)
        override fun getAllBooksStream(): Flow<List<Book>> = flowOf(emptyList())
        override fun getBookStream(id: Int): Flow<Book?> = flowOf(null)
        override suspend fun deleteBook(book: Book) {}
        override suspend fun updateBook(book: Book) {}
        override suspend fun insertBook(book: Book) {}
        override fun searchBooksByTitle(query: String): Flow<List<Book>> = flowOf(emptyList())
        override fun searchBooksByAuthor(query: String): Flow<List<Book>> = flowOf(emptyList())
        override fun searchBooksByGenre(query: String): Flow<List<Book>> = flowOf(emptyList())
        override fun searchBooksByMinRating(minRating: Float): Flow<List<Book>> = flowOf(emptyList())
        override suspend fun refreshBooksFromApi() {}
        override fun getTopUnreadBooks(): Flow<List<Book>> = flowOf(emptyList())
        override suspend fun getReadCount(): Int = 0
        override suspend fun getStartedCount(): Int = 0
        override suspend fun getNotStartedCount(): Int = 0
        override suspend fun getAllUnreadBooks(): List<Book> = emptyList()
    }

    @Before
    fun setup() {
        viewModel = HomeViewModel(fakeRepo)
    }

    @Test
    fun homeUiState_emits_sample_books() = runBlocking {
        // Wait for state flow to update (give viewModelScope time to emit)
        val actual = viewModel.homeUiState
            .drop(1) // Skip initial default value
            .first() // Wait for the first real emission

        assertEquals(sampleBooks, actual.bookList)
    }

}
