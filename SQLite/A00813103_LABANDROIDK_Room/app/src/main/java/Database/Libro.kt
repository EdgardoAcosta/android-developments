package Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.sql.Date

@Entity(tableName = "Libro")
@TypeConverters(Converters::class)
data class Libro(
        @ColumnInfo(name = "titulo") var titulo: String,
        @ColumnInfo(name = "isbn") var isbn: String,
        @ColumnInfo(name = "fecha_publicicacion") var fecha_pulicacion: Date?,
        @ColumnInfo(name = "imagen_libro") var imagenLibro: ByteArray) {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0
}
œ
// Data access object
@Dao
interface LibroDao {
    @Query("SELECT * FROM Libro ORDER BY titulo")
    fun loadAllBooks(): LiveData<List<Libro>>

    @Insert
    fun insertBookList(libroList: List<Libro>)

    @Insert
    fun insertBook(libro: Libro)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBook(libro: Libro)

    @Delete
    fun deleteBook(libro: Libro)

    @Query("SELECT * FROM Libro WHERE _id = :id")
    fun loadBookById(id: Int): LiveData<Libro>

}

@Database(entities = arrayOf(Libro::class), version = 1, exportSchema = false)
abstract class LibroDatabase_2: RoomDatabase(){
    abstract fun libroDao() : LibroDao
}