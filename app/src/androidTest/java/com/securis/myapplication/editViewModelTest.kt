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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class BookEditViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookDao: BookDao
    private lateinit var repository: BooksRepositoryImpl
    private lateinit var viewModel: BookEditViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val testBook = Book(
        id = 1,
        title = "Editable Book",
        author = "Author Name",
        genre = "Sci-Fi",
        userRating = 3,
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
        viewModel = BookEditViewModel(savedStateHandle, repository)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun bookUiState_initializes_with_existing_book() = runTest {
        testScheduler.advanceUntilIdle()
        val uiState = viewModel.bookUiState

        assertEquals(testBook.title, uiState.bookDetails.title)
        assertEquals(testBook.author, uiState.bookDetails.author)
        assertTrue(uiState.isEntryValid)
    }

    @Test
    fun updateUiState_sets_isEntryValid_false_for_blank_fields() {
        val invalidDetails = BookDetails(title = "", author = "")
        viewModel.updateUiState(invalidDetails)
        assertEquals(false, viewModel.bookUiState.isEntryValid)
    }

    @Test
    fun updateBook_updates_book_in_repository() = runTest {
        testScheduler.advanceUntilIdle()

        val updatedDetails = viewModel.bookUiState.bookDetails.copy(title = "Updated Title")
        viewModel.updateUiState(updatedDetails)

        viewModel.updateBook()
        testScheduler.advanceUntilIdle()

        val updatedBook = repository.getBookStream(testBook.id).first()
        assertNotNull(updatedBook)
        assertEquals("Updated Title", updatedBook.title)
    }
}
