package com.example.moviesteacomputers.data.network

interface NetworkCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}