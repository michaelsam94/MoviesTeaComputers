package com.example.moviesteacomputers.data

import com.example.moviesteacomputers.data.local.LocalDataSource
import com.example.moviesteacomputers.data.model.ContentRes
import com.example.moviesteacomputers.data.network.NetworkDataSource
import com.example.moviesteacomputers.data.network.POSTER_PREFIX
import com.example.moviesteacomputers.helper.NetworkAwareHandler
import com.example.moviesteacomputers.ui.model.Movie
import io.reactivex.rxjava3.core.Observable

class MovieRepositoryImp constructor(private val movieRemoteDataSource: NetworkDataSource,private val movieLocalDataSource: LocalDataSource,private val networkHandler:NetworkAwareHandler):
    MovieRepository {


    override fun getTrending(): Observable<List<Movie>> {
        return if(networkHandler.isOnline()){
            movieRemoteDataSource.getTrending().doAfterNext { movieLocalDataSource.cacheTrending(it) }.map { convertMovieRes(it,null) }
        } else {
            movieLocalDataSource.getTrending().map { it.map { it.toMovie() } }.toObservable()
        }
    }

    override fun searchMovies(q: String): Observable<List<Movie>> {
        return if(networkHandler.isOnline()) {
            movieRemoteDataSource.searchMovies(q).map { convertMovieRes(it,"movie") }
        } else {
            movieLocalDataSource.searchMovies(q).map { it.map { it.toMovie() } }.toObservable()
        }
    }

    override fun searchTV(q: String): Observable<List<Movie>> {
        return if(networkHandler.isOnline()) {
            movieRemoteDataSource.searchTV( q).map { convertMovieRes(it,"tv") }
        } else {
            movieLocalDataSource.searchTVs(q).map { it.map { it.toMovie() } }.toObservable()
        }
    }


    fun convertMovieRes(contentRes: ContentRes,type: String?): List<Movie> {
        val result: MutableList<Movie> = mutableListOf()
        contentRes.results.forEach {
            val movie = when (type ?: it.media_type) {
                "movie" -> Movie(it.title,"$POSTER_PREFIX${it.poster_path}",it.overview,it.vote_average,it.release_date,it.original_language)
                "tv" -> Movie(it.name,"$POSTER_PREFIX${it.poster_path}",it.overview,it.vote_average,it.first_air_date,it.original_language)
                else -> Movie(it.name,"$POSTER_PREFIX${it.poster_path}",it.overview,it.vote_average,it.release_date,it.original_language)
            }
            result.add(movie)
        }
        return result
    }





}