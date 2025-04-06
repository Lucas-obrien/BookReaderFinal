package com.securis.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.securis.myapplication.data.AppDatabase
import com.securis.myapplication.data.Book
import com.securis.myapplication.data.BookDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test




class Test(){

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: BookDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.bookDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertBookAndGetById_returnsCorrectBook() = runTest {
        val book = Book(title = "1984", author = "George Orwell", blurb = "Dystopia", genre = "Fiction", apiRating = 5)
        dao.insertBook(book)

        val result = dao.getBook(1).first()

        assertNotNull(result)
        assertEquals("1984", result?.title)
        assertEquals("George Orwell", result?.author)
    }

    @Test
    fun getFirstThreeBooks_returnsTop3Unread() = runTest {
        val books = listOf(
            Book(title = "Book A", author = "A", blurb = "", genre = "Fic", apiRating = 4, userRating = 3, read = false),
            Book(title = "Book B", author = "B", blurb = "", genre = "Fic", apiRating = 4, userRating = 5, read = false),
            Book(title = "Book C", author = "C", blurb = "", genre = "Fic", apiRating = 4, userRating = 2, read = false),
            Book(title = "Book D", author = "D", blurb = "", genre = "Fic", apiRating = 4, userRating = 4, read = false),
        )
        dao.insertAll(books)

        val result = dao.getFirstThreeBooks().first()

        assertEquals(3, result.size)
        assertEquals("Book B", result[0].title) // Highest userRating
    }
    @Test
    fun searchBooksByTitle_findsCorrectBook() = runTest {
        val books = listOf(
            Book(title = "The Hobbit", author = "Tolkien", blurb = "", genre = "Fantasy", apiRating = 5),
            Book(title = "The Lord of the Rings", author = "Tolkien", blurb = "", genre = "Fantasy", apiRating = 5)
        )
        dao.insertAll(books)

        val result = dao.searchBooksByTitle("%Hobbit%").first()

        assertEquals(1, result.size)
        assertEquals("The Hobbit", result[0].title)
    }
    @Test
    fun countByTitleAndAuthor_returnsCorrectCount() = runTest {
        val book = Book(title = "Sample", author = "Author", blurb = "", genre = "Genre", apiRating = 5)
        dao.insertBook(book)

        val count = dao.countByTitleAndAuthor("Sample", "Author")
        assertEquals(1, count)
    }


    @Test
    fun getRatedGenres_returnsGenresSortedByAverageUserRating() = runTest {
        dao.insertAll(listOf(
            Book(title = "A", author = "X", blurb = "", genre = "Sci-fi", apiRating = 5, userRating = 4),
            Book(title = "B", author = "Y", blurb = "", genre = "Fantasy", apiRating = 5, userRating = 5),
            Book(title = "C", author = "Z", blurb = "", genre = "Sci-fi", apiRating = 5, userRating = 2)
        ))

        val genres = dao.getRatedGenres()
        assertEquals(listOf("Fantasy", "Sci-fi"), genres)
    }

    @Test
    fun getAllGenres_returnsAllDistinctGenres() = runTest {
        dao.insertAll(listOf(
            Book(title = "A", author = "X", blurb = "", genre = "Sci-fi", apiRating = 5),
            Book(title = "B", author = "Y", blurb = "", genre = "Fantasy", apiRating = 5),
            Book(title = "C", author = "Z", blurb = "", genre = "Sci-fi", apiRating = 5)
        ))

        val genres = dao.getAllGenres()
        assertTrue(genres.contains("Sci-fi"))
        assertTrue(genres.contains("Fantasy"))
    }
    @Test
    fun getUnreadBooksByGenre_returnsCorrectList() = runTest {
        dao.insertAll(listOf(
            Book(title = "1", author = "A", blurb = "", genre = "Mystery", apiRating = 5, read = false),
            Book(title = "2", author = "B", blurb = "", genre = "Mystery", apiRating = 5, read = false),
            Book(title = "3", author = "C", blurb = "", genre = "Mystery", apiRating = 5, read = true)
        ))

        val unread = dao.getUnreadBooksByGenre("Mystery")
        assertEquals(2, unread.size)
    }
    @Test
    fun getReadCount_returnsCorrectNumber() = runTest {
        dao.insertAll(listOf(
            Book(title = "Read", author = "", blurb = "", genre = "", apiRating = 5, read = true),
            Book(title = "Unread", author = "", blurb = "", genre = "", apiRating = 5, read = false)
        ))

        val count = dao.getReadCount()
        assertEquals(1, count)
    }
    @Test
    fun getStartedCount_returnsCorrectNumber() = runTest {
        dao.insertAll(listOf(
            Book(title = "Started", author = "", blurb = "", genre = "", apiRating = 5, started = true),
            Book(title = "Finished", author = "", blurb = "", genre = "", apiRating = 5, started = true, read = true),
            Book(title = "Not Started", author = "", blurb = "", genre = "", apiRating = 5, started = false)
        ))

        val count = dao.getStartedCount()
        assertEquals(1, count)
    }
    @Test
    fun getNotStartedCount_returnsCorrectNumber() = runTest {
        dao.insertAll(listOf(
            Book(title = "NotStarted", author = "", blurb = "", genre = "", apiRating = 5),
            Book(title = "Started", author = "", blurb = "", genre = "", apiRating = 5, started = true)
        ))

        val count = dao.getNotStartedCount()
        assertEquals(1, count)
    }
    @Test
    fun getAllUnreadBooks_returnsUnreadBooksOnly() = runTest {
        dao.insertAll(listOf(
            Book(title = "Unread1", author = "", blurb = "", genre = "", apiRating = 5, read = false),
            Book(title = "Read", author = "", blurb = "", genre = "", apiRating = 5, read = true)
        ))

        val result = dao.getAllUnreadBooks()
        assertEquals(1, result.size)
        assertEquals("Unread1", result[0].title)
    }








}