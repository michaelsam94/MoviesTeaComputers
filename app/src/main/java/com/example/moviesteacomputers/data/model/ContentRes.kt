package com.example.moviesteacomputers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesteacomputers.data.network.POSTER_PREFIX
import com.example.moviesteacomputers.ui.model.Movie

data class ContentRes(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class Dates(
    val maximum: String,
    val minimum: String
)


@Entity
data class Result(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = listOf(),
    @PrimaryKey
    val id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val name: String= "",
    val video: Boolean = false,
    val vote_average: Float = 0.0f,
    val vote_count: Int = 0,
    val media_type: String = "",
    val first_air_date: String = ""
) {
    fun toMovie(): Movie {
        val movie = when (media_type) {
            "movie" -> Movie(title, "$POSTER_PREFIX${poster_path}", overview,vote_average,release_date,original_language)
            "tv" -> Movie(name, "$POSTER_PREFIX${poster_path}", overview,vote_average,first_air_date,original_language)
            else -> Movie(name, "$POSTER_PREFIX${poster_path}", overview,vote_average,release_date,original_language)
        }
        return movie
    }
}