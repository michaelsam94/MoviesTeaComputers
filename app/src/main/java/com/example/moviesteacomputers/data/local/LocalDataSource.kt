package com.example.moviesteacomputers.data.local

import com.example.moviesteacomputers.data.model.ContentRes
import com.example.moviesteacomputers.data.model.Result
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun getTrending(): Single<List<Result>>

    fun cacheTrending(data: ContentRes)

    fun searchMovies(q: String): Single<List<Result>>

    fun searchTVs(q: String): Single<List<Result>>

    fun deleteAllContent()
}