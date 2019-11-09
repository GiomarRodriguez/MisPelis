package com.team.spartan.mispelis.data.database

import androidx.room.*
import com.team.spartan.mispelis.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Single<Long>

    @Query("select * from movie")
    fun getAll(): Observable<List<Movie>>

    @Delete
    fun delete(movie: Movie): Completable
}