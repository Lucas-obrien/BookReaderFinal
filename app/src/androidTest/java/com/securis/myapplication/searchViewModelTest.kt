package com.securis.myapplication.ui.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.securis.myapplication.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SearchBooksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: SearchBooksViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val books = listOf(
        Book(
            id = 1,
            title = "Dune",
            author = "Frank Herbert",
            genre = "Sci-Fi",
            blurb = "Epic space adventure.",
            apiReview = "A timeless classic.",
            userReview = "Loved the political intrigue.",
            userRating = 5,
            apiRating = 5,
            read = true,
            started = false
        ),
        Book(
            id = 2,
            title = "Foundation",
            author = "Isaac Asimov",
            genre = "Sci-Fi",
            blurb = "The fall of the Galactic Empire.",
            apiReview = "Brilliant and ahead of its time.",
            userReview = "Fascinating ideas.",
            userRating = 4,
            apiRating = 4,
            read = false,
            started = true
        ),
        Book(
            id = 3,
            title = "Pride and Prejudice",
            author = "Jane Austen",
            genre = "Romance",
            blurb = "Love and society.",
            apiReview = "Charming and witty.",
            userReview = "A delightful read.",
            userRating = 3,
            apiRating = 3,
            read = true,
            started = false
        ),
        Book(
            id = 4,
            title = "High Rating",
            author = "Top Author",
            genre = "Nonfiction",
            blurb = "A deep dive into greatness.",
            apiReview = "Inspiring!",
            userReview = "10/10 would read again.",
            userRating = 5,
            apiRating = 5,
            read = false,
            started = false
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        bookDao = database.bookDao()
        repository = BooksRepositoryImpl(bookDao)

        runTest {
            bookDao.insertAll(books)
        }

        viewModel = SearchBooksViewModel(repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun searchByTitle_returns_correct_book() = runTest {
        viewModel.updateFilter("Title", "Dune")
        testScheduler.advanceUntilIdle()

        val results = viewModel.searchUiState.first().filteredBooks
        assertEquals(1, results.size)
        assertEquals("Dune", results.first().title)
    }

    @Test
    fun searchByAuthor_returns_correct_books() = runTest {
        viewModel.updateFilter("Author", "Isaac")
        testScheduler.advanceUntilIdle()

        val results = viewModel.searchUiState.first().filteredBooks
        assertEquals(1, results.size)
        assertEquals("Foundation", results.first().title)
    }

    @Test
    fun searchByGenre_returns_correct_books() = runTest {
        viewModel.updateFilter("Genre", "Sci-Fi")
        testScheduler.advanceUntilIdle()

        val results = viewModel.searchUiState.first().filteredBooks
        assertEquals(2, results.size)
    }

    @Test
    fun searchByRating_returns_books_with_min_rating() = runTest {
        viewModel.updateFilter("Rating", "4")
        testScheduler.advanceUntilIdle()

        val results = viewModel.searchUiState.first().filteredBooks
        assertEquals(3, results.size) // Dune, Foundation, High Rating
    }

    @Test
    fun searchByInvalidRating_returns_empty_list() = runTest {
        viewModel.updateFilter("Rating", "not_a_number")
        testScheduler.advanceUntilIdle()

        val results = viewModel.searchUiState.first().filteredBooks
        assertEquals(0, results.size)
    }
}
