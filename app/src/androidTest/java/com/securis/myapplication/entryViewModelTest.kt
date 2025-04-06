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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class BookEntryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: BookEntryViewModel
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

        viewModel = BookEntryViewModel(repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun updateUiState_sets_isEntryValid_true_with_valid_input() {
        val details = BookDetails(
            title = "A Good Title",
            author = "Some Author"
        )
        viewModel.updateUiState(details)
        assertTrue(viewModel.bookUiState.isEntryValid)
    }

    @Test
    fun updateUiState_sets_isEntryValid_false_with_blank_title() {
        val details = BookDetails(
            title = "",
            author = "Author"
        )
        viewModel.updateUiState(details)
        assertFalse(viewModel.bookUiState.isEntryValid)
    }

    @Test
    fun saveBook_inserts_book_into_repository() = runTest {
        val details = BookDetails(
            id = 99,
            title = "Brand New Book",
            author = "New Author",
            genre = "Drama",
            rating = 3,
            userRating = 5,
            userReview = "Nice read.",
            started = false,
            read = false
        )
        viewModel.updateUiState(details)

        viewModel.saveBook()
        testScheduler.advanceUntilIdle()

        val result = repository.getBookStream(99).first()
        assertEquals("Brand New Book", result?.title)
        assertEquals("New Author", result?.author)
        assertEquals(5, result?.userRating)
        assertEquals(3, result?.apiRating)
    }
}
