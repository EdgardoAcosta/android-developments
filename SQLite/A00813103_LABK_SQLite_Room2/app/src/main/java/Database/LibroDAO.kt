package Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

// Data access object
@Dao
interface BookDao {
    @Query("SELECT * FROM Libro ORDER BY titulo")
    fun loadAllBooks(): LiveData<List<Libro>>

    @Query("SELECT COUNT (*) FROM Libro")
    fun getAnyBook(): Int

    @Insert
    fun insertBookList(bookList: List<Libro>)

    @Insert
    fun insertBook(book: Libro)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBook(book: Libro)

    @Delete
    fun deleteBook(book: Libro)

    @Query("SELECT * FROM Libro WHERE _id = :id")
    fun loadBookById(id: Int): LiveData<Libro>

}