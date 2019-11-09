package com.team.spartan.mispelis.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.team.spartan.mispelis.data.model.Movie

@Database(entities = [Movie::class], version = 3)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private val lock = Any()
        private const val DATABASE_NAME = "mypelis_database"
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(application: Application): MovieDatabase {
            synchronized(MovieDatabase.lock) {
                if (MovieDatabase.INSTANCE == null) {
                    MovieDatabase.INSTANCE =
                        Room.databaseBuilder(application,
                            MovieDatabase::class.java,
                            MovieDatabase.DATABASE_NAME)
                            .build()
                }
            }
            return  INSTANCE!!
        }
    }
}