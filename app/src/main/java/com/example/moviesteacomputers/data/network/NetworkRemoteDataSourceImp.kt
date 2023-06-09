package com.example.moviesteacomputers.data.network

import com.example.moviesteacomputers.data.model.ContentRes
import io.reactivex.rxjava3.core.Observable

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6bdd92e829e5beb0f2902f834db79e10"
const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w185"

class NetworkRemoteDataSourceImp constructor(
    val movieService: MovieService,
) :
    NetworkDataSource {


    override fun getTrending(): Observable<ContentRes> {
        return movieService.getTrending()
    }

    override fun searchMovies(q: String): Observable<ContentRes> {
        return movieService.searchMovies(q)
    }

    override fun searchTV(q: String): Observable<ContentRes> {
        return movieService.searchTV(q)
    }

}