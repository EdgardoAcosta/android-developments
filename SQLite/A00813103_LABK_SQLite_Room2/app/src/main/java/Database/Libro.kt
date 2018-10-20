package Database

import android.arch.persistence.room.*
import java.util.*

@Entity(tableName = "Libro")
@TypeConverters(Converters::class)
data class Libro(
        @ColumnInfo(name = "titulo") var titulo: String,
        @ColumnInfo(name = "isbn") var isbn: String,
        @ColumnInfo(name = "fecha_publicicacion") var fecha_pulicacion: Date?,
        @ColumnInfo(name = "imagen_libro") var imagenLibro: ByteArray,
        @ColumnInfo(name = "imagen_libroPath") var imagenLibroPath: String?
) {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0
}


