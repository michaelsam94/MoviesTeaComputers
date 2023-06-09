package com.example.moviesteacomputers.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val title: String, val image: String, val overview: String,val voteAverage: Float,val releaseDate: String,val language: String): Parcelable