package com.example.moviesteacomputers.data.network


import com.example.moviesteacomputers.data.model.ContentRes
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    fun getNowPlaying() : Call<ContentRes>

    @GET("movie/now_playing")
    suspend fun getNowPlayingCoroutine(): ContentRes

    @GET("trending/all/day")
    fun getTrending(): Observable<ContentRes>

    @GET("search/movie?include_adult=false&language=en-US$&page=1")
    fun searchMovies(@Query("query") q: String): Observable<ContentRes>

    @GET("search/tv?include_adult=false&language=en-US$&page=1")
    fun searchTV(@Query("query") q: String): Observable<ContentRes>

}