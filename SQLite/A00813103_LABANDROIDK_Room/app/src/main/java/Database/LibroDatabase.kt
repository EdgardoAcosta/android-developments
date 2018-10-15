package Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = arrayOf(Libro::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LibroDatabase : RoomDatabase()
{
    abstract fun libroDao(): LibroDao

    companion object {
        private val DATABASE_NAME = "LibroDB.db"
        private var dbInstance: LibroDatabase? = null
    }

    @Synchronized
    fun getInstance(context: Context): LibroDatabase{
        if (dbInstance == null){
            dbInstance = buildDatabase(context)
        }
        return dbInstance!!
    }
    private fun buildDatabase(context: Context): LibroDatabase{
        return Room.databaseBuilder(context, LibroDatabase::class.java, DATABASE_NAME).build()

    }
}