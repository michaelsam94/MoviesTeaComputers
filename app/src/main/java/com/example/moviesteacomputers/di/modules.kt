package com.example.moviesteacomputers.di

import android.util.Log
import androidx.room.Room
import com.example.moviesteacomputers.data.network.NetworkDataSource
import com.example.moviesteacomputers.data.MovieRepository
import com.example.moviesteacomputers.data.MovieRepositoryImp
import com.example.moviesteacomputers.data.local.LocalDataSource
import com.example.moviesteacomputers.data.local.MovieDatabase
import com.example.moviesteacomputers.data.local.RoomLocalDataSource
import com.example.moviesteacomputers.data.network.API_KEY
import com.example.moviesteacomputers.data.network.BASE_URL
import com.example.moviesteacomputers.data.network.NetworkRemoteDataSourceImp
import com.example.moviesteacomputers.data.network.MovieService
import com.example.moviesteacomputers.helper.AppSchedulerProvider
import com.example.moviesteacomputers.helper.NetworkAwareHandler
import com.example.moviesteacomputers.helper.NetworkHandler
import com.example.moviesteacomputers.helper.SchedulerProvider
import com.example.moviesteacomputers.ui.main.MainViewModel
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModules = module {


    single {
        LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(Log.VERBOSE)
            .build()
    }


    single(named("OkHttpClient")) {
        val okHttpClientBuilder = with(OkHttpClient.Builder()) {
            this.addInterceptor {
                val req = it.request()
                val originalHttpUrl = req.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val reqBuilder = req.newBuilder().url(url)
                val newReq = reqBuilder.build()
                it.proceed(newReq)
            }
            this.addInterceptor(get<LoggingInterceptor>())
        }
        okHttpClientBuilder.build()
    }



    single(named("retrofit")) {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get(named("OkHttpClient")))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        retrofit.create(MovieService::class.java)
    }

    single(named("movieRemoteDataSource")){
        NetworkRemoteDataSourceImp(get(named("retrofit")))
    }


    single {
        Room.databaseBuilder(get(), MovieDatabase::class.java, "MOVIES_DB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<MovieDatabase>().getMovieDao() }
    single <LocalDataSource> { RoomLocalDataSource(get())  }
    single <NetworkAwareHandler> { NetworkHandler(get())  }


    single {
        MovieRepositoryImp(
            get(named("movieRemoteDataSource")),
            get(),
            get()
        )
    }

    factory<MovieRepository> {  MovieRepositoryImp(get(),get(),get()) }
    factory<NetworkDataSource> {  NetworkRemoteDataSourceImp(get(named("retrofit"))) }

    single<SchedulerProvider> { AppSchedulerProvider() }
}


val viewModelModule = module {
    viewModel { MainViewModel(get(),get()) }
}