package com.securis.myapplication.ui.viewModels

import app.cash.turbine.test
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BooksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Before
    fun setup() {
        val fakeRepo = object : BooksRepository {
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

        viewModel = HomeViewModel(fakeRepo)
    }

    @Test
    fun `homeUiState emits sample books`() = runTest {
        viewModel.homeUiState.test {
            skipItems(1) // first one is the default empty list
            val actual = awaitItem()
            assertEquals(sampleBooks, actual.bookList)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
