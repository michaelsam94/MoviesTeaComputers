package com.example.moviesteacomputers.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.moviesteacomputers.data.MovieRepository
import com.example.moviesteacomputers.data.Result
import com.example.moviesteacomputers.helper.Progress
import com.example.moviesteacomputers.helper.SchedulerProvider
import com.example.moviesteacomputers.helper.Success
import com.example.moviesteacomputers.helper.ViewState
import com.example.moviesteacomputers.ui.main.MainViewModel
import com.example.moviesteacomputers.ui.model.Movie
import com.example.moviesteacomputers.util.*
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify


class MainViewModelTest {

    @Mock
    private lateinit var mockRepo: MovieRepository

    @Mock
    private lateinit var mockSchedulerProvider: SchedulerProvider

    @Mock
    private lateinit var observer: Observer<ViewState>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var testScheduler: TestScheduler
    private lateinit var viewModel: MainViewModel
    private lateinit var mainStateLiveData: MutableLiveData<ViewState>
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.openMocks(this)
        testScheduler = TestScheduler()
        mainStateLiveData = MutableLiveData()


        // Set up the mock behavior
        `when`(mockRepo.getTrending()).thenReturn(Observable.just(listOf(Movie("c","d","a",3.5f,"20-03-2008","en"))))
        `when`(mockSchedulerProvider.io()).thenReturn(testScheduler)
        `when`(mockSchedulerProvider.ui()).thenReturn(testScheduler)
    }

    @After
    fun teardown() {
        // Dispose any ongoing subscriptions to avoid leaks
        viewModel.moviesDisposable?.dispose()
    }

    @Test
    fun getTrending_emitsSuccessState() {
        viewModel = MainViewModel(mockRepo, TestSchedulerProvider(testScheduler))
        viewModel.mainViewState.observeForever(observer)
        viewModel.getTrending()
        `when`(mockRepo.getTrending()).thenReturn(Observable.just(listOf(Movie("a","b","c",4.6f,"25-9-2000","en"))))
        verify(observer).onChanged(Progress)
        verify(observer).onChanged(Success(listOf(Movie("a","b","c",4.6f,"25-9-2000","en"))))
        // Invoke the function


        // Advance the test scheduler to trigger emissions
        testScheduler.triggerActions()

    }

    @Test
    fun getTrending_emitsErrorState() {
        // Set up the mock behavior to throw an exception
        val errorMessage = "Some error message"
        `when`(mockRepo.getTrending()).thenReturn(Observable.error(Exception(errorMessage)))

        // Invoke the function
        viewModel.getTrending()

        // Advance the test scheduler to trigger emissions
        testScheduler.triggerActions()

        // Verify the expected state is emitted
        assert(mainStateLiveData.value == Error(errorMessage))
    }
}