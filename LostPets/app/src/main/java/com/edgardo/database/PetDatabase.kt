package com.edgardo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = arrayOf(Pet::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDAO

    companion object {
        private const val DATABASE_NAME = "PetDB.db"
        private var dbInstance: PetDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PetDatabase {
            if (dbInstance == null) {
                dbInstance = buildDatabase(context)
            }
            return dbInstance!!
        }

        private fun buildDatabase(context: Context): PetDatabase{
            return Room.databaseBuilder(context, PetDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

        }
    }


}