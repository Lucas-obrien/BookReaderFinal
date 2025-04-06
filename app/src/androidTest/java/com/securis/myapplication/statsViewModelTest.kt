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
class StatsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: StatsViewModel
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
                        title = "Read Book",
                        author = "Author 1",
                        genre = "Fantasy",
                        blurb = "Blurb 1",
                        apiReview = "Review A",
                        userReview = "User Review A",
                        userRating = 5,
                        apiRating = 4,
                        read = true,
                        started = false
                    ),
                    Book(
                        id = 2,
                        title = "Started Book",
                        author = "Author 2",
                        genre = "Sci-Fi",
                        blurb = "Blurb 2",
                        apiReview = "Review B",
                        userReview = "User Review B",
                        userRating = 4,
                        apiRating = 3,
                        read = false,
                        started = true
                    ),
                    Book(
                        id = 3,
                        title = "Not Started Book",
                        author = "Author 3",
                        genre = "Romance",
                        blurb = "Blurb 3",
                        apiReview = "Review C",
                        userReview = "User Review C",
                        userRating = 3,
                        apiRating = 2,
                        read = false,
                        started = false
                    )
                )
            )
        }

        viewModel = StatsViewModel(repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun stats_are_loaded_correctly() = runTest {
        testScheduler.advanceUntilIdle()
        val uiState = viewModel.uiState.first()

        assertEquals(1, uiState.readCount)
        assertEquals(1, uiState.startedCount)
        assertEquals(1, uiState.notStartedCount)
    }
}
