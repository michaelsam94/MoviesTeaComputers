package com.example.moviesteacomputers.data.local

import com.example.moviesteacomputers.data.model.ContentRes
import com.example.moviesteacomputers.data.model.Result
import io.reactivex.rxjava3.core.Single

class RoomLocalDataSource(private val movieDao: MovieDao) : LocalDataSource {
    override fun getTrending(): Single<List<Result>> {
        return movieDao.getTrending()
    }

    override fun cacheTrending(data: ContentRes) {
        movieDao.insertTrending(data.results)
    }

    override fun searchMovies(q: String): Single<List<Result>> {
        return movieDao.searchMovies(q)
    }

    override fun searchTVs(q: String): Single<List<Result>> {
        return movieDao.searchTvs(q)
    }


    override fun deleteAllContent() {
        movieDao.deleteAllTrending()
    }

}