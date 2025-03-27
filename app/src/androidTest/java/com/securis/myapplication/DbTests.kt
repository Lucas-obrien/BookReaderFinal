package com.securis.myapplication

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import data.SecurisBookReaderAppDB
import data.SecurisBookReaderDao
import data.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DbTests {
    private lateinit var db: SecurisBookReaderAppDB
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var SecurisBookReaderDao: SecurisBookReaderDao

    @Before

    fun createDb() {
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(appContext, SecurisBookReaderAppDB::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        SecurisBookReaderDao = db.SecurisBookReaderDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    //runBlocking:
    //- Blocks the current thread until the coroutine completes.
    //- Used to bridge synchronous and asynchronous code.
    //- Creates a scope for coroutines, controlling their lifetime.
    //Key differences:
    //- suspend defines asynchronous functions.
    //- runBlocking executes coroutines synchronously.

    fun insertAndGetUser() = runBlocking {
        val user = User(username = "Tony", score = 100, duration = 50, date = 10000L)
        SecurisBookReaderDao.insert(user)
        val fetchedUser = SecurisBookReaderDao.getUserById(1)
        assertEquals("Tony", fetchedUser?.username)

    }



    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}