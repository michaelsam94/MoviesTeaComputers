package com.example.moviesteacomputers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesteacomputers.data.model.Result
import com.example.moviesteacomputers.helper.ArrayListConverter

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}