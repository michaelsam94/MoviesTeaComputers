package com.example.moviesteacomputers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesteacomputers.data.model.Result
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrending(items: List<Result>): List<Long>

    @Query("SELECT * FROM Result")
    fun getTrending(): Single<List<Result>>

    @Query("SELECT * FROM Result where title LIKE  '%' || :q || '%' AND media_type = 'movie'")
    fun searchMovies(q: String): Single<List<Result>>

    @Query("SELECT * FROM Result where name LIKE  '%' || :q || '%' AND media_type = 'movie'")
    fun searchTvs(q: String): Single<List<Result>>

    @Query("DELETE FROM Result")
    fun deleteAllTrending(): Completable
}