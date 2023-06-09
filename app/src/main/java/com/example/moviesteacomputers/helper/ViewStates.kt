package com.example.moviesteacomputers.helper

sealed class ViewState
data class Success<T>(val data: T?): ViewState()
object Progress: ViewState()
data class Error (val error: String?): ViewState()