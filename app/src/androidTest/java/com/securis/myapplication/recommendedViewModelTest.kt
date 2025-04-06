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
class RecommendedBookViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: RecommendedBookViewModel
    private val testDispatcher = StandardTestDispatcher()

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
            bookDao.insertAll(
                listOf(
                    Book(
                        id = 1,
                        title = "Unread Book 1",
                        author = "Author 1",
                        genre = "Fantasy",
                        blurb = "Blurb 1",
                        apiReview = "Review A",
                        userReview = "User Review A",
                        userRating = 5,
                        apiRating = 4,
                        read = false,
                        started = false
                    ),
                    Book(
                        id = 2,
                        title = "Unread Book 2",
                        author = "Author 2",
                        genre = "Fantasy",
                        blurb = "Blurb 2",
                        apiReview = "Review B",
                        userReview = "User Review B",
                        userRating = 4,
                        apiRating = 4,
                        read = false,
                        started = false
                    ),
                    Book(
                        id = 3,
                        title = "Read Book",
                        author = "Author 3",
                        genre = "Mystery",
                        blurb = "Blurb 3",
                        apiReview = "Review C",
                        userReview = "User Review C",
                        userRating = 5,
                        apiRating = 5,
                        read = true,
                        started = false
                    )
                )
            )
        }

        viewModel = RecommendedBookViewModel(repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun recommendedBooks_onlyIncludesUnreadBooks() = runTest {
        testScheduler.advanceUntilIdle()

        val books = viewModel.recommendedUiState.first().filteredBooks

        assertEquals(2, books.size)
        assertEquals("Unread Book 1", books[0].title)
        assertEquals("Unread Book 2", books[1].title)
        assertEquals(false, books.any { it.read })
    }
}
