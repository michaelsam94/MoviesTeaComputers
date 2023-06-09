package com.example.moviesteacomputers.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesteacomputers.data.MovieRepository
import com.example.moviesteacomputers.helper.*
import com.example.moviesteacomputers.ui.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainViewModel (private val repo: MovieRepository,private val schedulerProvider: SchedulerProvider) : ViewModel(){


    private val _mainState = MutableLiveData<ViewState>()
    val mainViewState: LiveData<ViewState> = _mainState



    lateinit var moviesDisposable: Disposable

    fun getIOSch(): Scheduler {
        return schedulerProvider.io()
    }

    fun getUiSch(): Scheduler {
        return schedulerProvider.ui()
    }

    fun getTrending(){
        _mainState.value = Progress
        moviesDisposable = repo.getTrending()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe ({
            _mainState.postValue(Success(it))
        },{
            _mainState.postValue(Error(it.message))
        })

    }





    fun search(q: String) {
        _mainState.value = Progress


        moviesDisposable = repo.searchMovies(q).zipWith(repo.searchTV(q)) { movies, tvs ->
            val res = ArrayList<Movie>()
            if(movies.size != tvs.size) {
                res.addAll(movies)
                res.addAll(tvs)
            } else {
                movies.zip(tvs).forEach { pair ->
                    res.add(pair.first)
                    res.add(pair.second)
                }
            }
            res.toList()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                _mainState.value = Success(it)
            },{
                _mainState.value = Error(it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        moviesDisposable.dispose()
    }

}