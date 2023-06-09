package com.example.moviesteacomputers.data.network

import com.example.moviesteacomputers.data.model.ContentRes
import io.reactivex.rxjava3.core.Observable

interface NetworkDataSource {

    fun getTrending(): Observable<ContentRes>

    fun searchMovies(q: String): Observable<ContentRes>

    fun searchTV(q: String): Observable<ContentRes>
}