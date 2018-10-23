package Database

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@Entity(tableName = "Libro")
@TypeConverters(Converters::class)
data class Libro(
        @ColumnInfo(name = "titulo") var titulo: String,
        @ColumnInfo(name = "isbn") var isbn: String,
        @ColumnInfo(name = "fecha_publicicacion") var fecha_pulicacion: Date?,
        @ColumnInfo(name = "imagen_libro") var imagenLibro: ByteArray,
        @ColumnInfo(name = "imagen_libroPath") var imagenLibroPath: String?
) : Parcelable {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            TODO("fecha_pulicacion"),
            parcel.createByteArray(),
            parcel.readString()) {
        _id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(isbn)
        parcel.writeByteArray(imagenLibro)
        parcel.writeString(imagenLibroPath)
        parcel.writeInt(_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Libro> {
        override fun createFromParcel(parcel: Parcel): Libro {
            return Libro(parcel)
        }

        override fun newArray(size: Int): Array<Libro?> {
            return arrayOfNulls(size)
        }
    }
}


