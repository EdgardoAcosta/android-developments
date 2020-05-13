package Database

import android.arch.persistence.room.*

    // Data access object
    @Dao
    interface BookDao {
        @Query("SELECT * FROM Libro ORDER BY titulo")
        fun loadAllBooks(): List<Libro>

        @Insert
        fun insertBookList(bookList: List<Libro>)

        @Insert
        fun insertBook(book: Libro)


        @Update(onConflict = OnConflictStrategy.REPLACE)
        fun updateBook(book: Libro)

        @Delete
        fun deleteBook(book: Libro)

        @Query("SELECT * FROM Libro WHERE _id = :id")
        fun loadBookById(id: Int): Libro

    }