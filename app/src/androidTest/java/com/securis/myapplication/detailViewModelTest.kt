package com.securis.myapplication.ui.viewModels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.securis.myapplication.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BookDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: BookDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val testBook = Book(
        id = 1,
        title = "Test Book",
        author = "Test Author",
        genre = "Fantasy",
        userRating = 5,
        apiRating = 4,
        started = false,
        read = false,
        blurb = "",
        apiReview = "4",
        userReview = "5"
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
            repository.insertBook(testBook)
        }

        val savedStateHandle = SavedStateHandle(mapOf("bookId" to testBook.id))
        viewModel = BookDetailViewModel(savedStateHandle, repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_shouldReturnCorrectBookDetails() = runTest {
        val state = viewModel.uiState.drop(1).first()
        assertEquals(testBook.title, state.bookDetails.title)
        assertEquals(testBook.author, state.bookDetails.author)
        assertEquals(testBook.userRating, state.bookDetails.userRating)
    }

    @Test
    fun deleteBook_shouldRemoveBookFromDb() = runTest {
        viewModel.deleteBook(testBook)
        testScheduler.advanceUntilIdle()

        val result = repository.getBookStream(testBook.id).first()
        assertEquals(null, result)
    }

    @Test
    fun markBookAsStarted_shouldUpdateBookStartedField() = runTest {
        viewModel.markBookAsStarted(testBook)
        testScheduler.advanceUntilIdle()

        val updated = repository.getBookStream(testBook.id).first()
        assertNotNull(updated)
        assertTrue(updated.started)
    }
}
