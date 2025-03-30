import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>
}