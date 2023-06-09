package com.example.moviesteacomputers.data

import com.example.moviesteacomputers.ui.model.Movie
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {

    fun getTrending(): Observable<List<Movie>>

    fun searchMovies(q: String): Observable<List<Movie>>

    fun searchTV(q: String): Observable<List<Movie>>
}