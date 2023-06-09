package com.example.moviesteacomputers.util

import com.example.moviesteacomputers.helper.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler


class TestSchedulerProvider(private val mTestScheduler: TestScheduler) : SchedulerProvider {

    override fun ui(): Scheduler {
        return mTestScheduler
    }

    override fun computation(): Scheduler {
        return mTestScheduler
    }

    override fun io(): Scheduler {
        return mTestScheduler
    }
}