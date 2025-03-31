import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>> // This returns a Flow

    @Insert
    suspend fun insert(book: Book)

    @Insert
    suspend fun insertAll(books: List<Book>) // Insert multiple books


}