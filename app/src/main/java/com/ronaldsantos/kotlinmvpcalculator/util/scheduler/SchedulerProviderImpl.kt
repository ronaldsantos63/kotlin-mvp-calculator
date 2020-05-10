package com.ronaldsantos.kotlinmvpcalculator.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by: Ronald Santos
 * Date: 05/08/2020
 * Time: 21:33
 */
object SchedulerProviderImpl : BaseSchedulerProvider {

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    override fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}